package com.github.amangusss.sustainable_voting_system.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoStorageService {

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        String original = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String ext = StringUtils.getFilenameExtension(original);
        String name = UUID.randomUUID().toString();
        if (ext != null && !ext.isBlank()) {
            name = name + "." + ext;
        }
        Path dir = Paths.get(uploadDir).toAbsolutePath();
        try {
            Files.createDirectories(dir);
            Path target = dir.resolve(name).normalize();
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to store file", ex);
        }
        return "/uploads/" + name;
    }
}

