package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Config;

import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.User;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if any admin exists to avoid creating duplicates every time you restart
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin"); // Your main username
            admin.setPassword(passwordEncoder.encode("admin123")); // Your main password
            admin.setRole("ROLE_ADMIN");
            admin.setSubDomainSlug("main-admin");

            userRepository.save(admin);
            System.out.println("Main Admin created successfully with username: admin");
        }
    }
}