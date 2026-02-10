package com.github.amangusss.sustainable_voting_system.service.impl;

import com.github.amangusss.sustainable_voting_system.dto.LoginRequest;
import com.github.amangusss.sustainable_voting_system.dto.RegisterRequest;
import com.github.amangusss.sustainable_voting_system.dto.SimpleMessageResponse;
import com.github.amangusss.sustainable_voting_system.entity.Voter;
import com.github.amangusss.sustainable_voting_system.repository.VoterRepository;
import com.github.amangusss.sustainable_voting_system.service.AuthService;
import com.github.amangusss.sustainable_voting_system.tunduk.TundukClient;
import com.github.amangusss.sustainable_voting_system.tunduk.TundukProfile;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final VoterRepository voterRepository;
    private final TundukClient tundukClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public SimpleMessageResponse register(RegisterRequest request) {
        if (request == null || request.passportId() == null || request.password() == null
                || request.fullName() == null) {
            throw new IllegalArgumentException("Invalid registration data");
        }
        if (voterRepository.existsByPassportId(request.passportId())) {
            log.warn("Registration attempt with existing passportId={}", request.passportId());
            throw new IllegalArgumentException("Passport is already registered");
        }
        Optional<TundukProfile> profile = tundukClient.fetchProfile(request.passportId());
        if (profile.isEmpty()) {
            log.warn("Tunduk verification failed for passportId={}", request.passportId());
            throw new IllegalArgumentException("Tunduk verification failed");
        }
        if (!profile.get().isAdult()) {
            log.warn("Underage registration blocked for passportId={}", request.passportId());
            throw new IllegalArgumentException("Voter must be 18+");
        }

        Voter voter = new Voter();
        voter.setFullName(request.fullName());
        voter.setPassportId(request.passportId());
        voter.setDistrict(profile.get().district());
        voter.setPasswordHash(passwordEncoder.encode(request.password()));
        voterRepository.save(voter);
        log.info("Voter registered passportId={} district={}", request.passportId(), voter.getDistrict());

        return new SimpleMessageResponse("Registration успешна");
    }

    @Override
    public SimpleMessageResponse login(LoginRequest request) {
        if (request == null || request.passportId() == null || request.password() == null) {
            throw new IllegalArgumentException("Invalid login data");
        }
        Voter voter = voterRepository.findByPassportId(request.passportId())
                .orElseThrow(() -> new IllegalArgumentException("Неверные данные входа"));
        if (!passwordEncoder.matches(request.password(), voter.getPasswordHash())) {
            log.warn("Login failed for passportId={}", request.passportId());
            throw new IllegalArgumentException("Неверные данные входа");
        }
        log.info("Login success passportId={}", request.passportId());
        return new SimpleMessageResponse("Вход выполнен");
    }
}
