package com.analyn.time.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class WhoIsIn {

    private String name;
    private String status;
    private LocalDate date;
    private LocalTime time;

    public WhoIsIn() {}

    public WhoIsIn(String name, String status, LocalDate date, LocalTime time) {
        this.name = name;
        this.status = status;
        this.date = date;
        this.time = time;
    }
}
