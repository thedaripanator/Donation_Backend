package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String donorName;
    private Double amount;
    private String PhoneNumber;

    @ManyToOne
    @JoinColumn(name = "sub_admin_id")
    private User subAdmin;


}