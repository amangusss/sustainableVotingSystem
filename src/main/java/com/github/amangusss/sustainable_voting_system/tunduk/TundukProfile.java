package com.github.amangusss.sustainable_voting_system.tunduk;

public record TundukProfile(String district, int age) {

    public boolean isAdult() {
        return age >= 18;
    }
}

