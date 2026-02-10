package com.github.amangusss.sustainable_voting_system.dto;

public record CandidateCreateRequest(String fullName,
                                     String party,
                                     String district,
                                     String photoUrl) {
}

