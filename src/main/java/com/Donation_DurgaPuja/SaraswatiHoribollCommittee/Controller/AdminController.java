package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Controller;

import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.Donation;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.UpdateRequest;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.User;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Service.AdminService;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private DonationService donationService;


    // Register subadmin
    @PostMapping("/create-subadmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createSubAdmin(@RequestBody User subAdminRequest) {
        User savedUser = adminService.registerSubAdmin(subAdminRequest);
        return ResponseEntity.ok(savedUser.getUsername());
    }

    // Getting all the subdomain
    @GetMapping("/sub_admin_details")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllSubAdmins() {
        List<User> users=adminService.getsubadmin();
        return ResponseEntity.ok(users);
    }

    //fetching the donation by the donation id
    @GetMapping("/agent/{id}/donations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Donation>> getSpecificAgentDonations(@PathVariable Long id) {
        List<Donation> details = donationService.getDonationsByAgentId(id);
        return ResponseEntity.ok(details);
    }
    //fetching all the donations info
    @GetMapping("/donations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Donation>> getAllDonations() {
        List<Donation> detials=donationService.getallDonationdetails();
        return ResponseEntity.ok(detials);
    }


    // Editing the info of the subadmin
    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> editSubAdmin(@PathVariable Long id, @RequestBody UpdateRequest updateData) {
        User updatedUser = adminService.update(id, updateData.getPassword());
        return ResponseEntity.ok(updatedUser);
    }
    // Freezing the SubAdmin
    @PatchMapping("/{id}/freeze")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> freezeUser(@PathVariable Long id) {
        adminService.toggleFreeze(id);
        return ResponseEntity.ok("User status updated successfully");
    }

    //UnFreezing the subAdmin
    @PatchMapping("/{id}/unfreeze")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> unfreezeUser(@PathVariable Long id) {
        adminService.toggleunfreeze(id);
        return ResponseEntity.ok("User status updated successfully");
    }

}