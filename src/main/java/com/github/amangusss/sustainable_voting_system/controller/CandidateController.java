package com.github.amangusss.sustainable_voting_system.controller;

import com.github.amangusss.sustainable_voting_system.dto.CandidateResponse;
import com.github.amangusss.sustainable_voting_system.service.CandidateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/candidates")
public class CandidateController {

    private static final Logger log = LoggerFactory.getLogger(CandidateController.class);

    private final CandidateService candidateService;

    @GetMapping
    public ResponseEntity<List<CandidateResponse>> getAll(
            @RequestParam(name = "district", required = false) String district) {
        log.debug("Get candidates district={}", district);
        return ResponseEntity.ok(candidateService.getAll(district));
    }
}
