package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Service;

import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.Donation;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.User;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository.DonationRepository;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    // updating the password by the admin
    public User update(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Registering the sub _admin
    public  User registerSubAdmin(User subAdmin) {
        subAdmin.setPassword(passwordEncoder.encode(subAdmin.getPassword()));
        subAdmin.setRole("ROLE_SUBADMIN");

        return userRepository.save(subAdmin);
    }

    //Fetching the data from the backend to know the details of the sub_admin
    public List<User> getsubadmin() {
        List<User> subadmins=userRepository.findByRole("ROLE_SUBADMIN");
        return subadmins;
    }


}