package com.github.amangusss.sustainable_voting_system.dto;

public record ResultResponse(Long id,
                             String fullName,
                             String district,
                             String party,
                             String photoUrl,
                             long photoVersion,
                             int voteCount) {
}
