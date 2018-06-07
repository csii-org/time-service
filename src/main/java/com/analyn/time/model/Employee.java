package com.analyn.time.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(exclude = "employeeTimes")
public class Employee {

    private @Id int id;
    private String fullName;
    private String fingerprintId;
    private String pin;
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "employee")
    private List<EmployeeTime> employeeTimes;

    private Employee() {}

    private Employee(int id, String fullName, String fingerprintId, String pin, Date createdDate, Company company) {
        this.id = id;
        this.fullName = fullName;
        this.fingerprintId = fingerprintId;
        this.pin = pin;
        this.createdDate = createdDate;
        this.company = company;
    }

}