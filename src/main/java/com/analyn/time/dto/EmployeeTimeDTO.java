package com.analyn.time.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EmployeeTimeDTO {
    private int empId;
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;
    private Double hoursWorked;
    private Double overtime;
    private Double undertime;
    private Double absent;
    private String notes;
    private String employeeName;
    private String leaveType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;
    private List<Integer> employeeList;
    private LocalDateTime input;

    public EmployeeTimeDTO(int empId, LocalDateTime timeIn, LocalDateTime timeOut, Double hoursWorked, Double overtime,
                           Double undertime, Double absent, String notes, String employeeName, String leaveType,
                           LocalDate dateFrom, LocalDate dateTo, List<Integer> employeeList, LocalDateTime input) {
        this.empId = empId;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.hoursWorked = hoursWorked;
        this.overtime = overtime;
        this.undertime = undertime;
        this.absent = absent;
        this.notes = notes;
        this.employeeName = employeeName;
        this.leaveType = leaveType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.employeeList = employeeList;
        this.input = input;
    }

    public EmployeeTimeDTO() {
    }


}
