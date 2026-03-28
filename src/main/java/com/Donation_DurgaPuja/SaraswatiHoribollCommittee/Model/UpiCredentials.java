package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "upi_credentials")
@Data
public class UpiCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String upiId;
    private String displayName;

    private boolean active = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}