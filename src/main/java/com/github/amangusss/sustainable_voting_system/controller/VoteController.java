package com.github.amangusss.sustainable_voting_system.controller;

import com.github.amangusss.sustainable_voting_system.dto.SimpleMessageResponse;
import com.github.amangusss.sustainable_voting_system.dto.VoteRequest;
import com.github.amangusss.sustainable_voting_system.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

    private static final Logger log = LoggerFactory.getLogger(VoteController.class);

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<SimpleMessageResponse> vote(@RequestBody VoteRequest request) {
        log.debug("Vote request received passportId={} candidateId={}", request != null ? request.passportId() : null, request != null ? request.candidateId() : null);
        return ResponseEntity.ok(voteService.vote(request));
    }
}
