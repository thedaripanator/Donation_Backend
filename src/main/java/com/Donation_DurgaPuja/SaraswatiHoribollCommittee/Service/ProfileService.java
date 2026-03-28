package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Service;

import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.UpiCredentials;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.UpiRequest;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.User;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository.ProfileRepository;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    // 1. Logic to add a new UPI
    @Transactional
    public UpiCredentials addNewUpi(String username, UpiRequest upiData) {
        if (profileRepository.existsByUpiId(upiData.getUpiId())) {
            throw new RuntimeException("This UPI ID is already registered!");
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UpiCredentials credential = new UpiCredentials();
        credential.setUpiId(upiData.getUpiId());
        credential.setDisplayName(upiData.getDisplayName());
        credential.setUser(user);

        boolean hasExisting = profileRepository.existsByUserId(user.getId());
        credential.setActive(!hasExisting);

        return profileRepository.save(credential);
    }

    public List<UpiCredentials> getAllUpisForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return profileRepository.findByUserId(user.getId());
    }

    // 3. Logic to switch the "Active" variable
    @Transactional
    public void setUpiActive(String username, Long upiId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        profileRepository.deactivateAllByUserId(user.getId());

        UpiCredentials target = profileRepository.findById(upiId)
                .orElseThrow(() -> new RuntimeException("UPI Credential not found"));

        // Security Check: Verify ownership
        if (!target.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized: This UPI does not belong to you.");
        }

        target.setActive(true);
        profileRepository.save(target);
    }
    public void deleteUpi(Long upiId) {
        profileRepository.deleteById(upiId);
    }
}