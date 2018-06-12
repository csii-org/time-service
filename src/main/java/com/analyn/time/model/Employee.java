package com.analyn.time.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(exclude = "employeeTimes")
public class Employee {

    private @Id int id;
    private String fullName;
    private String fingerprintId;
    private String pin;
    private LocalDateTime createdDate = LocalDateTime.now();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "company_id")
    private Company company;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<EmployeeTime> employeeTimes;

    private Employee() {}

    private Employee(int id, String fullName, String fingerprintId, String pin, LocalDateTime createdDate, Company company) {
        this.id = id;
        this.fullName = fullName;
        this.fingerprintId = fingerprintId;
        this.pin = pin;
        this.createdDate = createdDate;
        this.company = company;
    }

}