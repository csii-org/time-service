package com.analyn.time.dao;

import com.analyn.time.model.Employee;
import com.analyn.time.model.EmployeeTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
public interface EmployeeTimeRepository extends CrudRepository<EmployeeTime, Integer> {

    @Query("SELECT et from EmployeeTime et where et.employee.id=:employeeId and et.timeOut is null and et.timeIn between :start and :end ")
    List<EmployeeTime> findByEmployeeAndTimeInOut(@Param("employeeId") Integer employeeId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
