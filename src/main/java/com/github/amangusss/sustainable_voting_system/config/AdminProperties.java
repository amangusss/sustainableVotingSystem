package com.github.amangusss.sustainable_voting_system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.admin")
public record AdminProperties(String username, String password) {
}

