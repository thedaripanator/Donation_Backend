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
        // 1. Find the Agent in the DB based on the logged-in username
        User agent = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Agent not found in database"));

        // 2. Link the donation to this specific Agent
        donation.setSubAdmin(agent);

        // 3. Save the record
        donationRepository.save(donation);
    }

    public List<Donation> getDonationdetails(String username) {
        // 1. Identify the agent
        User agent = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        // 2. Ask the repository for ONLY this agent's donations
        return donationRepository.findBySubAdmin(agent);
    }

    public List<Donation> getDonationsByAgentId(Long agentId) {
        // 1. Find the agent first to make sure they exist
        User agent = userRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent with ID " + agentId + " not found"));

        // 2. Return the list of donations for that specific agent
        return donationRepository.findBySubAdmin(agent);
    }
}