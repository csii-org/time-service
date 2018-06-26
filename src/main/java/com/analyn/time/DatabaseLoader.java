package com.analyn.time;

import com.analyn.time.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final UserRepository users;

    @Autowired
    public DatabaseLoader(UserRepository repo) {
        this.users = repo;
    }

    @Override
    public void run(String... strings) throws Exception {
//        this.users.save(new User((long)1,"csi_admin", "adminp@sswordcsi1", "ADMIN"));
//        this.users.save(new User((long)2,"ccm_admin", "adminp@sswordccm2", "ADMIN"));
//        this.users.save(new User((long)3,"device_admin", "adminp@sswordbio3", "ADMIN"));
    }
}
