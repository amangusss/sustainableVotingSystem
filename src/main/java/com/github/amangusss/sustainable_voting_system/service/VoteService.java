package com.github.amangusss.sustainable_voting_system.service;

import com.github.amangusss.sustainable_voting_system.dto.SimpleMessageResponse;
import com.github.amangusss.sustainable_voting_system.dto.VoteRequest;

public interface VoteService {

    SimpleMessageResponse vote(VoteRequest request);
}

