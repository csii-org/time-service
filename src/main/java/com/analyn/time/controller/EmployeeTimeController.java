package com.analyn.time.controller;

import com.analyn.time.dao.EmployeeRepository;
import com.analyn.time.dao.EmployeeTimeRepository;
import com.analyn.time.dto.EmployeeTimeDTO;
import com.analyn.time.dto.WhoIsIn;
import com.analyn.time.exception.EmployeeNotFoundException;
import com.analyn.time.exception.TimeInNotFoundException;
import com.analyn.time.model.Employee;
import com.analyn.time.model.EmployeeTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@BasePathAwareController
public class EmployeeTimeController {

    private final EmployeeTimeRepository repository;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeTimeController(EmployeeTimeRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/timeIn")
    @ResponseBody
    public ResponseEntity<Object> timeIn(@RequestBody EmployeeTimeDTO body) {
    //public String timeIn(@RequestBody EmployeeTimeDTO body) {
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
        repository.save(timeOut);

        return ResponseEntity.ok().build();
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
