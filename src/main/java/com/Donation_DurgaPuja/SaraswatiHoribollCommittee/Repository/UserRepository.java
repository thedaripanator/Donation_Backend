package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository;

// IMPORT YOUR ENTITY, NOT SPRING'S USER
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.User;
import org.apache.catalina.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {



    Optional<User> findByUsername(String username);

    List<User> findByRole(String role);

    boolean existsByUsername(String username);
}
