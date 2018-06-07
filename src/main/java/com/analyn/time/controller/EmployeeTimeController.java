package com.analyn.time.controller;

import com.analyn.time.dao.EmployeeRepository;
import com.analyn.time.dao.EmployeeTimeRepository;
import com.analyn.time.dto.EmployeeTimeDTO;
import com.analyn.time.model.Employee;
import com.analyn.time.model.EmployeeTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
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
        if (employee.isPresent()) {
//            List<EmployeeTime> byTimeIn = repository.findByTimeInAndEmployee(body.getConvertedDate(), employee.get());
//            return byTimeIn.toString();
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
        } else {
            return ResponseEntity.notFound().build();
            //return "not found";
        }

    }

//    @RequestMapping(method = RequestMethod.GET, value = "/timeIt/out/{empId}/{timeIt}")
//    public @ResponseBody String timeOut(@PathVariable int empId, @PathVariable long timeIt) {
//        //Date timeOut = Date.from(Instant.ofEpochSecond(timeIt).t);
//        return empId + " " + timeOut;
//    }

}
