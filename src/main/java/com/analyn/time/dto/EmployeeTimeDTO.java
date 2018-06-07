package com.analyn.time.dto;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class EmployeeTimeDTO {
    private int empId;
    private long time;

    public EmployeeTimeDTO() {}

    public EmployeeTimeDTO(int empId, long time) {
        this.empId = empId;
        this.time = time;
    }

    public LocalDate getConvertedDate() {
        return Instant.ofEpochSecond(time).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDateTime getConvertedDateTime() {
        return Instant.ofEpochSecond(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
