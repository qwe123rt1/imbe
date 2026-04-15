package com.rohit.clinic.service;

import com.rohit.clinic.dto.request.FindMatchesRequest;
import com.rohit.clinic.dto.response.MatchResultResponse;
import java.util.List;

public interface MatchFindingService {

    List<MatchResultResponse> findTopMatches(FindMatchesRequest request);
}
