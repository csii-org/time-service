package com.analyn.time.dao;

import com.analyn.time.model.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
public interface CompanyRepository extends CrudRepository<Company, Integer> {
    @RestResource(path = "findByName", rel = "findByName")
    List<Company> findByNameIgnoreCaseContaining(@Param("name") String name);
}


