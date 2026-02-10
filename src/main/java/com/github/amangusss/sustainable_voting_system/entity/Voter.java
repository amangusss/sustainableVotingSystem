package com.github.amangusss.sustainable_voting_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "voters", uniqueConstraints = {
        @UniqueConstraint(name = "uk_voter_passport_id", columnNames = "passport_id")
})
@Getter
@Setter
@NoArgsConstructor
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "passport_id", nullable = false)
    private String passportId;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "is_voted", nullable = false)
    private boolean isVoted = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}

