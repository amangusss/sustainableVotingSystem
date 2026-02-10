package com.github.amangusss.sustainable_voting_system.dto;

public record VoteRequest(String passportId,
                          String password,
                          Long candidateId) {
}

