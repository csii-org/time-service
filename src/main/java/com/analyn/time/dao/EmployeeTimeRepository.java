package com.analyn.time.dao;

import com.analyn.time.model.EmployeeTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
public interface EmployeeTimeRepository extends PagingAndSortingRepository<EmployeeTime, Integer>, QuerydslPredicateExecutor<EmployeeTime> {

    @Query("SELECT et from EmployeeTime et where et.employee.id=:employeeId and et.timeOut is null and et.timeIn between :start and :end ")
    List<EmployeeTime> findByEmployeeAndTimeInOut(@Param("employeeId") Integer employeeId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT et from EmployeeTime et where et.timeIn between :dateTodayStart and :dateTodayEnd or et.timeOut between :dateTodayStart and :dateTodayEnd ")
    List<EmployeeTime> findWhoIsINOut(@Param("dateTodayStart") LocalDateTime start, @Param("dateTodayEnd") LocalDateTime end);

}
