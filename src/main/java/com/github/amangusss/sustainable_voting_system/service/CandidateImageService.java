package com.github.amangusss.sustainable_voting_system.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class CandidateImageService {

    private static final Logger log = LoggerFactory.getLogger(CandidateImageService.class);
    private static final String PLACEHOLDER_PATH = "images/placeholder/placeholder.png";

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    @Cacheable(value = "candidateImages", key = "#filename + ':' + #version")
    public CachedImage loadImage(String filename, long version) {
        if (filename == null || filename.isBlank()) {
            return loadPlaceholder();
        }
        Path file = Paths.get(uploadDir).toAbsolutePath().resolve(filename).normalize();
        if (!Files.exists(file)) {
            return loadPlaceholder();
        }
        try {
            String contentType = Files.probeContentType(file);
            if (contentType == null || contentType.isBlank()) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }
            byte[] bytes = Files.readAllBytes(file);
            log.debug("Loaded image filename={} size={} bytes", filename, bytes.length);
            return new CachedImage(bytes, contentType);
        } catch (IOException ex) {
            log.warn("Failed to read image filename={} due to {}", filename, ex.getMessage());
            return loadPlaceholder();
        }
    }

    private CachedImage loadPlaceholder() {
        try {
            ClassPathResource resource = new ClassPathResource(PLACEHOLDER_PATH);
            byte[] bytes = resource.getInputStream().readAllBytes();
            return new CachedImage(bytes, MediaType.IMAGE_PNG_VALUE);
        } catch (IOException ex) {
            log.warn("Failed to read placeholder image due to {}", ex.getMessage());
            return new CachedImage(new byte[0], MediaType.APPLICATION_OCTET_STREAM_VALUE);
        }
    }

    public record CachedImage(byte[] bytes, String contentType) {
    }
}
