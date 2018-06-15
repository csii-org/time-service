package com.analyn.time.controller;

import com.analyn.time.dao.EmployeeRepository;
import com.analyn.time.dao.EmployeeTimeRepository;
import com.analyn.time.dto.EmployeeTimeDTO;
import com.analyn.time.dto.WhoIsIn;
import com.analyn.time.exception.EmployeeNotFoundException;
import com.analyn.time.exception.NotFoundException;
import com.analyn.time.exception.TimeInNotFoundException;
import com.analyn.time.model.Employee;
import com.analyn.time.model.EmployeeTime;
import com.analyn.time.model.QEmployeeTime;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@BasePathAwareController
public class EmployeeTimeController {

    private final EmployeeTimeRepository repository;

    private final EmployeeRepository employeeRepository;

    private static final double REGULAR_WORK_HOURS = 9;
    private static final double LUNCH_BREAK = 1;

    @Autowired
    public EmployeeTimeController(EmployeeTimeRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

//    @RequestMapping(method = RequestMethod.POST, value = "/employeeTimes")
//    @ResponseBody
//    public ResponseEntity<Object> save(@RequestBody EmployeeTime record) {
//
//    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, value = "/employeeTimes/{id}")
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody EmployeeTimeDTO input) {
        Optional<EmployeeTime> record = repository.findById(id);
        if (!record.isPresent()) {
            throw new NotFoundException("Record not found - id: " + id);
        }
        EmployeeTime updateRecord = record.get();
        updateRecord.setNotes((input.getNotes() != null) ? input.getNotes() : updateRecord.getNotes());
        updateRecord.setTimeIn((input.getTimeIn() != null) ? input.getTimeIn() : updateRecord.getTimeIn());
        updateRecord.setTimeOut((input.getTimeOut() != null) ? input.getTimeOut() : updateRecord.getTimeOut());
        updateRecord.setUpdatedDate(LocalDateTime.now());
        if (updateRecord.getTimeOut() != null) {
            computeHours(updateRecord);
        }
        repository.save(updateRecord);

        return ResponseEntity.ok(updateRecord);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/employeeTimes/search/listView")
    @ResponseBody
    public ResponseEntity<Object> listView(EmployeeTimeDTO param) {
        QEmployeeTime employeeTime = QEmployeeTime.employeeTime;
        Predicate predicate = null;
        if (param.getEmployeeName() != null) {
            predicate = employeeTime.employee.fullName.containsIgnoreCase(param.getEmployeeName());
        }
        if (param.getDateFrom() != null && param.getDateTo() == null) {
            Predicate timePredicate = employeeTime.timeIn.after(LocalDateTime.of(param.getDateFrom(), LocalTime.MIDNIGHT));
            predicate = (predicate != null) ? ((BooleanExpression) predicate).and(timePredicate) : timePredicate;
        } else if (param.getDateFrom() != null && param.getDateTo() != null) {
            Predicate rangePredicate = employeeTime.timeIn.between(LocalDateTime.of(param.getDateFrom(), LocalTime.MIDNIGHT), LocalDateTime.of(param.getDateTo(), LocalTime.MAX));
            predicate = (predicate != null) ? ((BooleanExpression) predicate).and(rangePredicate) : rangePredicate;
        }
        if (param.getLeaveType() != null) {
            Predicate leavePredicate = employeeTime.leaveType.containsIgnoreCase(param.getLeaveType());
            predicate = (predicate != null) ? ((BooleanExpression) predicate).and(leavePredicate) : leavePredicate;
        }
        Iterable<EmployeeTime> result = repository.findAll(predicate);
        return ResponseEntity.ok(result);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/timeIn")
    @ResponseBody
    public ResponseEntity<Object> timeIn(@RequestBody EmployeeTimeDTO body) {
        Optional<Employee> employee = employeeRepository.findById(body.getEmpId());
        if (!employee.isPresent()) {
            throw new EmployeeNotFoundException("Employee ID not found: " + body.getEmpId());
        }
        EmployeeTime newTime = new EmployeeTime();
        LocalDateTime now = LocalDateTime.now();
        newTime.setEmployee(employee.get());
        newTime.setCreatedDate(now);
        newTime.setTimeIn(now);
        newTime.setUpdatedDate(now);
        EmployeeTime created = repository.save(newTime);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).build();


    }

    @RequestMapping(method = RequestMethod.POST, value = "/timeOut")
    @ResponseBody
    public ResponseEntity<Object> timeOut(@RequestBody EmployeeTimeDTO body) {
        Optional<Employee> employee = employeeRepository.findById(body.getEmpId());

        if (!employee.isPresent()) {
            throw new EmployeeNotFoundException("Employee ID not found: " + body.getEmpId());
        }
        LocalDate curDate = LocalDate.now();
        LocalDateTime startTime = LocalDateTime.of(curDate.minusDays(1), LocalTime.MIDNIGHT);
        LocalDateTime endTime = LocalDateTime.of(curDate, LocalTime.MAX);
        List<EmployeeTime> result = repository.findByEmployeeAndTimeInOut(employee.get().getId(), startTime, endTime);
        if (result == null || result.isEmpty()) {
            throw new TimeInNotFoundException("No time in found");
        }

        EmployeeTime timeOut = result.get(0);
        LocalDateTime now = LocalDateTime.now();
        timeOut.setTimeOut(now);
        timeOut.setUpdatedDate(now);
        computeHours(timeOut);
        repository.save(timeOut);

        return ResponseEntity.ok().build();
    }

    private void computeHours(EmployeeTime time) {
        Long workMinutes = ChronoUnit.MINUTES.between(time.getTimeIn(), time.getTimeOut());
        Double workHours = workMinutes.doubleValue() / 60;
        time.setHoursWorked(workHours - LUNCH_BREAK);
        if (workHours > REGULAR_WORK_HOURS) {
            time.setOvertime(workHours - REGULAR_WORK_HOURS);
            time.setUndertime(null);
        } else if (workHours < REGULAR_WORK_HOURS) {
            time.setUndertime(REGULAR_WORK_HOURS - workHours);
            time.setOvertime(null);
        } else {
            time.setOvertime(null);
            time.setUndertime(null);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/whoIsIn")
    @ResponseBody
    public Resources<WhoIsIn> whoIsIn() {
        LocalDate curDate = LocalDate.now();
        LocalDateTime startTime = LocalDateTime.of(curDate, LocalTime.MIDNIGHT);
        LocalDateTime endTime = LocalDateTime.of(curDate, LocalTime.MAX);
        List<EmployeeTime> result = repository.findWhoIsINOut(startTime, endTime);
        List<WhoIsIn> response = new ArrayList<>();

        if (result != null && !result.isEmpty()) {
            for (EmployeeTime row : result) {
                WhoIsIn whoIsIn = new WhoIsIn();
                whoIsIn.setName(row.getEmployee().getFullName());
                whoIsIn.setDate(curDate);
                if (row.getTimeOut() != null ) {
                    whoIsIn.setStatus("OUT");
                    whoIsIn.setTime(row.getTimeOut().toLocalTime());
                } else {
                    whoIsIn.setStatus("IN");
                    whoIsIn.setTime(row.getTimeIn().toLocalTime());
                }
                response.add(whoIsIn);
            }
        }

        return new Resources<>(response);
    }

}
