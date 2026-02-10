package com.github.amangusss.sustainable_voting_system.dto;

public record RegisterRequest(String fullName,
                              String passportId,
                              String password) {
}

