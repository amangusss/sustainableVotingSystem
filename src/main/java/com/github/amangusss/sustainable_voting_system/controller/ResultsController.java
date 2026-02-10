package com.github.amangusss.sustainable_voting_system.controller;

import com.github.amangusss.sustainable_voting_system.dto.ResultResponse;
import com.github.amangusss.sustainable_voting_system.service.ResultsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/results")
public class ResultsController {

    private final ResultsService resultsService;

    @GetMapping
    public ResponseEntity<List<ResultResponse>> getResults(
            @RequestParam(name = "candidateName", required = false) String candidateName) {
        return ResponseEntity.ok(resultsService.getResults(candidateName));
    }
}
