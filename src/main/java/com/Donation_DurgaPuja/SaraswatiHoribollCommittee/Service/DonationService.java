package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Service;

import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.Donation;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.User;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository.DonationRepository;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private UserRepository userRepository;

    public void collectManualDonation(Donation donation, String username) {

        User agent = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Agent not found in database"));
        String rawPhone = donation.getPhoneNumber().replaceAll("[^0-9]", "");
        if (rawPhone.length() > 10) {
            rawPhone = rawPhone.substring(rawPhone.length() - 10);
        }
        donation.setPhoneNumber(rawPhone);

        donation.setSubAdmin(agent);
        donationRepository.save(donation);
    }

    public List<Donation> getDonationdetails(String username) {

        User agent = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        return donationRepository.findBySubAdmin(agent);
    }

    public List<Donation> getDonationsByAgentId(Long agentId) {
        // 1. Find the agent first to make sure they exist
        User agent = userRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent with ID " + agentId + " not found"));


        return donationRepository.findBySubAdmin(agent);
    }
    // Getting the details of all Donation details
    public List<Donation> getallDonationdetails() {
        return donationRepository.findAll();
    }
}