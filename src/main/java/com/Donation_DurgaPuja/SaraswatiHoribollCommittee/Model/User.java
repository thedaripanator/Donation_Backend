package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") // "user" is a reserved keyword in some DBs, "users" is safer
@Data // Lombok annotation to create Getters/Setters
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role;

    @Column(name = "frozen")
    private boolean frozen;


}