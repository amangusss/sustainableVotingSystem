package com.github.amangusss.sustainable_voting_system.repository;

import com.github.amangusss.sustainable_voting_system.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsByVoterId(Long voterId);
}

