package com.analyn.time.dto;

import lombok.Data;

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

    public EmployeeTimeDTO() {
    }

    public EmployeeTimeDTO(int empId, LocalDateTime timeIn, LocalDateTime timeOut, Double hoursWorked, Double overtime, Double undertime, Double absent, String notes) {
        this.empId = empId;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.hoursWorked = hoursWorked;
        this.overtime = overtime;
        this.undertime = undertime;
        this.absent = absent;
        this.notes = notes;
    }

}
