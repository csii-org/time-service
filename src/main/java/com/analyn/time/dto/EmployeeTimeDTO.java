package com.analyn.time.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;

    public EmployeeTimeDTO(int empId, LocalDateTime timeIn, LocalDateTime timeOut, Double hoursWorked, Double overtime, Double undertime, Double absent, String notes, String employeeName, LocalDate dateFrom, LocalDate dateTo) {
        this.empId = empId;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.hoursWorked = hoursWorked;
        this.overtime = overtime;
        this.undertime = undertime;
        this.absent = absent;
        this.notes = notes;
        this.employeeName = employeeName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public EmployeeTimeDTO() {
    }


}
