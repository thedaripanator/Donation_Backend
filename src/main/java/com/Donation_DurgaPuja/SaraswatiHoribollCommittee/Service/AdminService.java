package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Service;

import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.Donation;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.User;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository.DonationRepository;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // updating the password by the admin
    @Transactional
    public User update(Long id, String newRawPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (newRawPassword != null && !newRawPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newRawPassword));
        }

        return userRepository.save(user);
    }
    // Registering the sub _admin
    public User registerSubAdmin(User subAdmin) {
        if (userRepository.existsByUsername(subAdmin.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }
        String rawPassword = subAdmin.getPassword();
        subAdmin.setPassword(passwordEncoder.encode(rawPassword));
        subAdmin.setRole("ROLE_SUBADMIN");
        return userRepository.save(subAdmin);
    }

    //Fetching the data from the backend to know the details of the sub_admin
    public List<User> getsubadmin() {
        List<User> subadmins=userRepository.findByRole("ROLE_SUBADMIN");
        return subadmins;
    }

    @Transactional
    public void toggleFreeze(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Flip the boolean (if true, becomes false; if false, becomes true)
        user.setEnabled(!user.isEnabled());

        userRepository.save(user);
    }


}