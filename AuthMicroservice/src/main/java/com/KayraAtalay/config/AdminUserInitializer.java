package com.KayraAtalay.config;

import com.KayraAtalay.model.User;
import com.KayraAtalay.repository.UserRepository;
import com.KayraAtalay.shared.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // If there is no admin in DB create one
        if (!userRepository.findByUsername("admin").isPresent()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("Admin123123"));
            admin.setCreateTime(new Date());
            admin.setRole(UserRole.ADMIN);

            userRepository.save(admin);
            System.out.println("--- DEFAULT ADMIN USER CREATED ---");
        }
    }
}