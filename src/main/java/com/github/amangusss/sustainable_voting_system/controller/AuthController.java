package com.github.amangusss.sustainable_voting_system.controller;

import com.github.amangusss.sustainable_voting_system.dto.LoginRequest;
import com.github.amangusss.sustainable_voting_system.dto.RegisterRequest;
import com.github.amangusss.sustainable_voting_system.dto.SimpleMessageResponse;
import com.github.amangusss.sustainable_voting_system.service.AuthService;
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
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<SimpleMessageResponse> register(@RequestBody RegisterRequest request) {
        log.debug("Register request received passportId={}", request != null ? request.passportId() : null);
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<SimpleMessageResponse> login(@RequestBody LoginRequest request) {
        log.debug("Login request received passportId={}", request != null ? request.passportId() : null);
        return ResponseEntity.ok(authService.login(request));
    }
}
