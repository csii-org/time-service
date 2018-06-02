package com.analyn.time.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Entity
public class Employee {

    private @Id int id;
    private String fullName;
    private String fingerprintId;
    private String pin;
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

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