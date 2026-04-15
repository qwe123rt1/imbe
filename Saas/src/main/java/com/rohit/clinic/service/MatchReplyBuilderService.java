package com.rohit.clinic.service;

import com.rohit.clinic.dto.response.MatchResultResponse;
import java.util.List;

public interface MatchReplyBuilderService {

    String buildReplyMessage(List<MatchResultResponse> matches);
}
