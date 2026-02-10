package com.github.amangusss.sustainable_voting_system.service;

import com.github.amangusss.sustainable_voting_system.dto.CandidateCreateRequest;
import com.github.amangusss.sustainable_voting_system.dto.CandidateResponse;
import java.util.List;

public interface CandidateService {

    CandidateResponse create(CandidateCreateRequest request);

    List<CandidateResponse> getAll(String district);
}
