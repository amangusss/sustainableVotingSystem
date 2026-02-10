package com.github.amangusss.sustainable_voting_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "candidates")
@Getter
@Setter
@NoArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "party", nullable = false)
    private String party;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "photo_version", nullable = false)
    private long photoVersion = 1L;

    @Column(name = "vote_count", nullable = false)
    private int voteCount = 0;
}
