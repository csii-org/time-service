package com.analyn.time.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "inlineCompany", types = Employee.class)
public interface InlineCompany {
    Integer getId();
    String getFullName();
    String getFingerprintId();
    String getPin();
    Company getCompany();
}
