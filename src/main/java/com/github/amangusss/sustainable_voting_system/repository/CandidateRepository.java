package com.github.amangusss.sustainable_voting_system.repository;

import com.github.amangusss.sustainable_voting_system.entity.Candidate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findByFullNameContainingIgnoreCase(String fullName);

    List<Candidate> findByDistrictIgnoreCase(String district);
}
