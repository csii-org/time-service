package com.analyn.time.dao;

import com.analyn.time.model.User;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UserRepository extends Repository<User, Long> {
    User save(User user);

    User findByName(String name);
}
