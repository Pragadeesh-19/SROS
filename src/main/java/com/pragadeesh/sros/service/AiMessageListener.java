package com.pragadeesh.sros.service;

import com.pragadeesh.sros.dto.MessageQueueRequest;
import com.pragadeesh.sros.entity.RealityConfiguration;
import com.pragadeesh.sros.entity.SyntheticReality;
import com.pragadeesh.sros.repository.ConfigurationRepository;
import com.pragadeesh.sros.repository.RealityRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiMessageListener {

    private final ConfigurationRepository configurationRepository;
    private final RealityRepository realityRepository;
    private final LLMService llmService;

    @RabbitListener(queues = "${queue.ai.request}")
    public void receiveMessage(MessageQueueRequest messageQueueRequest) {
        System.out.println("Message Received " + messageQueueRequest.getRealityId());

        SyntheticReality reality = realityRepository.findById(messageQueueRequest.getRealityId())
                .orElse(null);

        if (reality != null) {
            RealityConfiguration configuration = reality.getConfiguration();
            if (configuration != null) {
                String worldData = llmService.generateWorldData(messageQueueRequest);
                configuration.setWorldData(worldData);
                configurationRepository.save(configuration);
            }
        }
    }
}
