package com.github.amangusss.sustainable_voting_system.service.impl;

import com.github.amangusss.sustainable_voting_system.dto.SimpleMessageResponse;
import com.github.amangusss.sustainable_voting_system.dto.VoteRequest;
import com.github.amangusss.sustainable_voting_system.entity.Candidate;
import com.github.amangusss.sustainable_voting_system.entity.Vote;
import com.github.amangusss.sustainable_voting_system.entity.Voter;
import com.github.amangusss.sustainable_voting_system.repository.CandidateRepository;
import com.github.amangusss.sustainable_voting_system.repository.VoteRepository;
import com.github.amangusss.sustainable_voting_system.repository.VoterRepository;
import com.github.amangusss.sustainable_voting_system.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private static final Logger log = LoggerFactory.getLogger(VoteServiceImpl.class);

    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;
    private final VoteRepository voteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public SimpleMessageResponse vote(VoteRequest request) {
        if (request == null || request.passportId() == null || request.password() == null
                || request.candidateId() == null) {
            throw new IllegalArgumentException("Invalid vote data");
        }
        Voter voter = voterRepository.findByPassportId(request.passportId())
                .orElseThrow(() -> new IllegalArgumentException("Voter not found"));
        if (!passwordEncoder.matches(request.password(), voter.getPasswordHash())) {
            log.warn("Vote denied: invalid credentials passportId={}", request.passportId());
            throw new IllegalArgumentException("Неверные данные входа");
        }
        if (voter.isVoted() || voteRepository.existsByVoterId(voter.getId())) {
            log.warn("Vote denied: already voted passportId={}", request.passportId());
            return new SimpleMessageResponse("Извините, вы не можете повторно голосовать");
        }
        Candidate candidate = candidateRepository.findById(request.candidateId())
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));
        if (!candidate.getDistrict().equalsIgnoreCase(voter.getDistrict())) {
            log.warn("Vote denied: district mismatch passportId={} voterDistrict={} candidateDistrict={}",
                    request.passportId(), voter.getDistrict(), candidate.getDistrict());
            return new SimpleMessageResponse("Вы можете голосовать только за кандидатов своего района");
        }

        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setCandidate(candidate);
        voteRepository.save(vote);

        candidate.setVoteCount(candidate.getVoteCount() + 1);
        voter.setVoted(true);
        candidateRepository.save(candidate);
        voterRepository.save(voter);
        log.info("Vote accepted passportId={} candidateId={}", request.passportId(), candidate.getId());

        return new SimpleMessageResponse("Спасибо за ваш голос");
    }
}
