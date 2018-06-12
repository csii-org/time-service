package com.analyn.time.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(exclude = "employees")
public class Company {

    private @Id @GeneratedValue int id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<Employee> employees;

    private Company() {}

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }
}