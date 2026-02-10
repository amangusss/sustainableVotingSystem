package com.github.amangusss.sustainable_voting_system.controller;

import com.github.amangusss.sustainable_voting_system.dto.CandidateCreateRequest;
import com.github.amangusss.sustainable_voting_system.dto.CandidateResponse;
import com.github.amangusss.sustainable_voting_system.service.CandidateService;
import com.github.amangusss.sustainable_voting_system.service.PhotoStorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/candidates")
public class AdminCandidateController {

    private static final Logger log = LoggerFactory.getLogger(AdminCandidateController.class);

    private final CandidateService candidateService;
    private final PhotoStorageService photoStorageService;

    @PostMapping
    public ResponseEntity<CandidateResponse> create(@RequestBody CandidateCreateRequest request) {
        log.debug("Admin create candidate fullName={} district={}",
                request != null ? request.fullName() : null,
                request != null ? request.district() : null);
        return ResponseEntity.ok(candidateService.create(request));
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CandidateResponse> createWithUpload(
            @RequestPart("fullName") String fullName,
            @RequestPart("party") String party,
            @RequestPart("district") String district,
            @RequestPart(value = "photoUrl", required = false) String photoUrl,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
        String storedUrl = photoStorageService.store(photo);
        String resolvedUrl = storedUrl != null ? storedUrl : photoUrl;
        log.debug("Admin upload candidate fullName={} district={} hasPhoto={}",
                fullName, district, storedUrl != null);
        return ResponseEntity.ok(candidateService.create(
                new CandidateCreateRequest(fullName, party, district, resolvedUrl)
        ));
    }
}
