package com.analyn.time.dao;

import com.analyn.time.model.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Integer> {

    @RestResource(path = "findByName", rel = "findByName")
    List<Employee> findByFullNameIgnoreCaseContaining(@Param("name") String name);
}


