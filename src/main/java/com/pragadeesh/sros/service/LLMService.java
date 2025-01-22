package com.pragadeesh.sros.service;

import com.pragadeesh.sros.dto.MessageQueueRequest;

public interface LLMService {

    String generateWorldData(MessageQueueRequest request);
}
