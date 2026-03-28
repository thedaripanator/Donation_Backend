package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @GetMapping("/me")
    @PreAuthorize("hasRole('SUBADMIN')")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Not Authenticated");
        }
        return ResponseEntity.ok(Map.of(
                "username", userDetails.getUsername(),
                "role", userDetails.getAuthorities().toString()
        ));
    }
}