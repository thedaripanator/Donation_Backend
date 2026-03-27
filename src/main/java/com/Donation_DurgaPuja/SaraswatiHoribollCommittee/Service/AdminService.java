package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Service;

import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.Donation;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.User;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository.DonationRepository;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerSubAdmin(User subAdmin) {
        subAdmin.setPassword(passwordEncoder.encode(subAdmin.getPassword()));

        // 2. Assign the role
        subAdmin.setRole("ROLE_SUBADMIN");

        // 3. Generate the slug for the donation link
        if (subAdmin.getSubDomainSlug() == null || subAdmin.getSubDomainSlug().isEmpty()) {
            // Converts "North Committee" to "north-committee"
            String generatedSlug = subAdmin.getUsername().toLowerCase().trim().replaceAll("\\s+", "-");
            subAdmin.setSubDomainSlug(generatedSlug);
        }

        return userRepository.save(subAdmin);
    }

    public List<User> getsubadmin() {
        List<User> subadmins=userRepository.findByRole("ROLE_SUBADMIN");
        return subadmins;
    }


}