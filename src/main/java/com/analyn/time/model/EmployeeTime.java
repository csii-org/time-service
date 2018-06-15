package com.analyn.time.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class EmployeeTime {

    private @Id @GeneratedValue int id;
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;
    private Double hoursWorked;
    private Double overtime;
    private Double undertime;
    private Double absent;
    private String leaveType;
    private String notes;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public EmployeeTime() {}

    private EmployeeTime(int id, LocalDateTime timeIn, LocalDateTime timeOut, Double hoursWorked, Double overtime, Double undertime, Double absent, String leaveType, String notes, LocalDateTime createdDate, LocalDateTime updatedDate, Employee employee) {
        this.id = id;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.hoursWorked = hoursWorked;
        this.overtime = overtime;
        this.undertime = undertime;
        this.absent = absent;
        this.leaveType = leaveType;
        this.notes = notes;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.employee = employee;
    }


}