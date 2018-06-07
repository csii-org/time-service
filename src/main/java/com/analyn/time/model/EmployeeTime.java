package com.analyn.time.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class EmployeeTime {

    private @Id @GeneratedValue int id;
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;
    private Double overtime;
    private Double undertime;
    private Boolean isAbsent;
    private Date createdDate;
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public EmployeeTime() {}

    private EmployeeTime(int id, LocalDateTime timeIn, LocalDateTime timeOut, Double overtime, Double undertime, Boolean isAbsent, Date createdDate, Date updatedDate, Employee employee) {
        this.id = id;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.overtime = overtime;
        this.undertime = undertime;
        this.isAbsent = isAbsent;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.employee = employee;
    }


}