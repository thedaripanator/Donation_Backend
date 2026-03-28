package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository;

import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.UpiCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<UpiCredentials, Long> {

    // 1. Fixed the deactivation logic with a custom JPQL query
    @Modifying
    @Transactional // Ensures the update is committed
    @Query("UPDATE UpiCredentials u SET u.active = false WHERE u.user.id = :userId")
    void deactivateAllByUserId(@Param("userId") Long userId);

    // 2. Standard find method (Spring handles this automatically)
    List<UpiCredentials> findByUserId(Long id);

    // 3. Standard exists check (Spring handles this automatically)
    boolean existsByUserId(Long id);

    boolean existsByUpiId(String upiId);
}