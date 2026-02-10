package com.github.amangusss.sustainable_voting_system.controller;

import com.github.amangusss.sustainable_voting_system.service.CandidateImageService;
import com.github.amangusss.sustainable_voting_system.service.CandidateImageService.CachedImage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images/candidates")
public class CandidateImageController {

    private static final Logger log = LoggerFactory.getLogger(CandidateImageController.class);

    private final CandidateImageService candidateImageService;

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable String filename,
            @RequestParam(name = "v", defaultValue = "0") long version) {
        CachedImage image = candidateImageService.loadImage(filename, version);
        log.debug("Serve candidate image filename={} version={} size={}", filename, version, image.bytes().length);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, image.contentType())
                .body(image.bytes());
    }
}

