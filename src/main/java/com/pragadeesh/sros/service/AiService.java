package com.pragadeesh.sros.service;

import com.pragadeesh.sros.dto.AiRequest;
import com.pragadeesh.sros.dto.AiResponse;

public interface AiService {
    AiResponse generateWorldData(AiRequest aiRequest);
    AiResponse updateWorldData(AiRequest aiRequest);
}
