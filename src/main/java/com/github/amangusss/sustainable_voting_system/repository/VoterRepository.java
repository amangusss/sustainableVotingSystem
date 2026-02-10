package com.github.amangusss.sustainable_voting_system.repository;

import com.github.amangusss.sustainable_voting_system.entity.Voter;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<Voter, Long> {

    Optional<Voter> findByPassportId(String passportId);

    boolean existsByPassportId(String passportId);
}

