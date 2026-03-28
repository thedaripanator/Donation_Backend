package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Controller;

import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.UpiCredentials;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.UpiRequest;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subadmin/profile")
public class SubAdminProfileController {

    @Autowired
    private ProfileService profileService;

    // 1. Register a new UPI ID using @AuthenticationPrincipal
    @PostMapping("/upi")
    @PreAuthorize("hasRole('SUBADMIN')")
    public ResponseEntity<UpiCredentials> registerUpi(
            @RequestBody UpiRequest upiData,
            @AuthenticationPrincipal UserDetails currentUser) {

        UpiCredentials saved = profileService.addNewUpi(currentUser.getUsername(), upiData);
        return ResponseEntity.ok(saved);
    }

    // 2. Fetch all registered UPIs for the logged-in user
    @GetMapping("/upi-list")
    @PreAuthorize("hasRole('SUBADMIN')")
    public ResponseEntity<List<UpiCredentials>> getMyUpis(@AuthenticationPrincipal UserDetails currentUser) {
        return ResponseEntity.ok(profileService.getAllUpisForUser(currentUser.getUsername()));
    }

    // 3. Activate one specific UPI
    @PatchMapping("/upi/{id}/activate")
    @PreAuthorize("hasRole('SUBADMIN')")
    public ResponseEntity<String> activateUpi(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails currentUser) {

        profileService.setUpiActive(currentUser.getUsername(), id);
        return ResponseEntity.ok("UPI ID activated for collection.");
    }

    @DeleteMapping("/upi/delete/{id}")
    @PreAuthorize("hasRole('SUBADMIN')")
    public ResponseEntity<String> deleteUpi(@PathVariable Long id){
        profileService.deleteUpi(id);
        return ResponseEntity.ok("UPI ID deleted.");
    }
}