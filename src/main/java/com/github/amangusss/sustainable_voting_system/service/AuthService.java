package com.github.amangusss.sustainable_voting_system.service;

import com.github.amangusss.sustainable_voting_system.dto.LoginRequest;
import com.github.amangusss.sustainable_voting_system.dto.RegisterRequest;
import com.github.amangusss.sustainable_voting_system.dto.SimpleMessageResponse;

public interface AuthService {

    SimpleMessageResponse register(RegisterRequest request);

    SimpleMessageResponse login(LoginRequest request);
}

