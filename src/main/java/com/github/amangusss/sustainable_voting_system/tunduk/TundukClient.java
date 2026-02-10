package com.github.amangusss.sustainable_voting_system.tunduk;

import java.util.Optional;

public interface TundukClient {

    Optional<TundukProfile> fetchProfile(String passportId);
}

