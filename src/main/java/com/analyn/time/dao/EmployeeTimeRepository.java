package com.analyn.time.dao;

import com.analyn.time.model.EmployeeTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface EmployeeTimeRepository extends CrudRepository<EmployeeTime, Integer> {
}
