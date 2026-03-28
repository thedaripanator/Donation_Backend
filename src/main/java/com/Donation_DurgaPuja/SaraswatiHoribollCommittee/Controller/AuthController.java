package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Controller;


import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
        @Autowired
        private AuthenticationManager authenticationManager;

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody UpdateRequest loginRequest) {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Extract role
            String role = userDetails.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority()
                    .replace("ROLE_", "");

            return ResponseEntity.ok(
                    Map.of(
                            "username", userDetails.getUsername(),
                            "role", role
                    )
            );
        }
    }


