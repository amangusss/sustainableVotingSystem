package com.github.amangusss.sustainable_voting_system.dto;

public record CandidateResponse(Long id,
                                String fullName,
                                String party,
                                String district,
                                String photoUrl,
                                long photoVersion,
                                int voteCount) {
}
