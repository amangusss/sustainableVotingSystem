package com.github.amangusss.sustainable_voting_system.service;

import com.github.amangusss.sustainable_voting_system.dto.ResultResponse;
import java.util.List;

public interface ResultsService {

    List<ResultResponse> getResults(String candidateName);
}

