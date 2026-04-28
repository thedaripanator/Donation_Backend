package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository;

import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.Donation;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation,Long> {
    List<Donation> findBySubAdmin(User agent);
}
