package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Service;

import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.User;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Fetch the user from your MySQL database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found in DB: " + username));

        // 2. Return a Spring Security 'UserDetails' object
        // This tells Spring: "Yes, this user exists, and here is their hashed password."
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().replace("ROLE_", "")) // Removes 'ROLE_' because Spring adds it back
                .build();
    }
}