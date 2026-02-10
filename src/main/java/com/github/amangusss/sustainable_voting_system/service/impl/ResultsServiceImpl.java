package com.github.amangusss.sustainable_voting_system.service.impl;

import com.github.amangusss.sustainable_voting_system.dto.ResultResponse;
import com.github.amangusss.sustainable_voting_system.entity.Candidate;
import com.github.amangusss.sustainable_voting_system.repository.CandidateRepository;
import com.github.amangusss.sustainable_voting_system.service.ResultsService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ResultsServiceImpl implements ResultsService {

    private final CandidateRepository candidateRepository;

    @Override
    public List<ResultResponse> getResults(String candidateName) {
        List<Candidate> candidates;
        if (candidateName == null || candidateName.isBlank()) {
            candidates = candidateRepository.findAll();
        } else {
            candidates = candidateRepository.findByFullNameContainingIgnoreCase(candidateName);
        }
        return candidates.stream()
                .map(candidate -> new ResultResponse(
                        candidate.getId(),
                        candidate.getFullName(),
                        candidate.getDistrict(),
                        candidate.getParty(),
                        resolvePhotoUrl(candidate),
                        candidate.getPhotoVersion(),
                        candidate.getVoteCount()
                ))
                .collect(Collectors.toList());
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
