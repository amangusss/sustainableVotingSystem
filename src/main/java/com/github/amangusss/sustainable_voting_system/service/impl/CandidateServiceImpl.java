package com.github.amangusss.sustainable_voting_system.service.impl;

import com.github.amangusss.sustainable_voting_system.dto.CandidateCreateRequest;
import com.github.amangusss.sustainable_voting_system.dto.CandidateResponse;
import com.github.amangusss.sustainable_voting_system.entity.Candidate;
import com.github.amangusss.sustainable_voting_system.repository.CandidateRepository;
import com.github.amangusss.sustainable_voting_system.service.CandidateService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private static final Logger log = LoggerFactory.getLogger(CandidateServiceImpl.class);

    private final CandidateRepository candidateRepository;

    @Override
    public CandidateResponse create(CandidateCreateRequest request) {
        if (request == null || request.fullName() == null || request.party() == null || request.district() == null) {
            throw new IllegalArgumentException("Invalid candidate data");
        }
        Candidate candidate = new Candidate();
        candidate.setFullName(request.fullName());
        candidate.setParty(request.party());
        candidate.setDistrict(request.district());
        candidate.setPhotoUrl(request.photoUrl());
        if (request.photoUrl() != null && !request.photoUrl().isBlank()) {
            candidate.setPhotoVersion(System.currentTimeMillis());
        }
        Candidate saved = candidateRepository.save(candidate);
        log.info("Candidate created id={} fullName={} district={}", saved.getId(), saved.getFullName(), saved.getDistrict());
        return toResponse(saved);
    }

    @Override
    public List<CandidateResponse> getAll(String district) {
        List<Candidate> candidates;
        if (district == null || district.isBlank()) {
            candidates = candidateRepository.findAll();
        } else {
            candidates = candidateRepository.findByDistrictIgnoreCase(district);
        }
        return candidates.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private CandidateResponse toResponse(Candidate candidate) {
        return new CandidateResponse(
                candidate.getId(),
                candidate.getFullName(),
                candidate.getParty(),
                candidate.getDistrict(),
                resolvePhotoUrl(candidate),
                candidate.getPhotoVersion(),
                candidate.getVoteCount()
        );
    }

    private String resolvePhotoUrl(Candidate candidate) {
        String photoUrl = candidate.getPhotoUrl();
        if (photoUrl == null || photoUrl.isBlank()) {
            return null;
        }
        if (photoUrl.startsWith("/uploads/")) {
            String filename = StringUtils.getFilename(photoUrl);
            if (filename != null && !filename.isBlank()) {
                return "/images/candidates/" + filename + "?v=" + candidate.getPhotoVersion();
            }
        }
        return photoUrl;
    }
}
