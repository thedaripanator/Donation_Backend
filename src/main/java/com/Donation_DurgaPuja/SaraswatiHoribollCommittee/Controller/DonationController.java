package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Controller;

import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.Donation;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subadmin")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @PostMapping("/collect")
    @PreAuthorize("hasRole('SUBADMIN')")
    public ResponseEntity<String> collectDonation(
            @RequestBody Donation donation,
            @AuthenticationPrincipal UserDetails currentUser) {

        // Use the service to save the donation linked to the logged-in user
        donationService.collectManualDonation(donation, currentUser.getUsername());

        return ResponseEntity.ok("Donation recorded successfully by agent: " + currentUser.getUsername());
    }
    @GetMapping("/fetch_donation")
    @PreAuthorize("hasRole('SUBADMIN')")
    public ResponseEntity<List<Donation>> fetchDonation(@AuthenticationPrincipal UserDetails currentUser) {

        List<Donation> logs=donationService.getDonationdetails(currentUser.getUsername());
        return ResponseEntity.ok(logs);

    }
}