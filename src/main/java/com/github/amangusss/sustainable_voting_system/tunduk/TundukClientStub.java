package com.github.amangusss.sustainable_voting_system.tunduk;

import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class TundukClientStub implements TundukClient {

    @Override
    public Optional<TundukProfile> fetchProfile(String passportId) {
        if (passportId == null || passportId.isBlank() || passportId.startsWith("X")) {
            return Optional.empty();
        }
        int age = passportId.endsWith("00") ? 16 : 19;
        String district = "Bishkek";
        return Optional.of(new TundukProfile(district, age));
    }
}

