package com.analyn.time.controller;

import com.analyn.time.dao.EmployeeRepository;
import com.analyn.time.dao.EmployeeTimeRepository;
import com.analyn.time.dto.EmployeeTimeDTO;
import com.analyn.time.model.Employee;
import com.analyn.time.model.EmployeeTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
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
    public ResponseEntity<Object> timeIn(@RequestBody EmployeeTimeDTO body) {
        Optional<Employee> employee = employeeRepository.findById(body.getEmpId());
        if (employee.isPresent()) {
            EmployeeTime newTime = new EmployeeTime();
            Date now = new Date();
            newTime.setEmployee(employee.get());
            newTime.setCreatedDate(now);
            newTime.setTimeIn(body.getConvertedDateTime());
            newTime.setUpdatedDate(now);
            EmployeeTime created = repository.save(newTime);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

//    @RequestMapping(method = RequestMethod.GET, value = "/time/out/{empId}/{time}")
//    public @ResponseBody String timeOut(@PathVariable int empId, @PathVariable long time) {
//        //Date timeOut = Date.from(Instant.ofEpochSecond(time).t);
//        return empId + " " + timeOut;
//    }

}
