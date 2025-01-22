package com.pragadeesh.sros.service;

import com.pragadeesh.sros.dto.MessageQueueRequest;
import com.pragadeesh.sros.entity.RealityConfiguration;
import com.pragadeesh.sros.entity.SyntheticReality;
import com.pragadeesh.sros.entity.User;
import com.pragadeesh.sros.repository.ConfigurationRepository;
import com.pragadeesh.sros.repository.RealityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealityService {

    private final RealityRepository realityRepository;
    private final ConfigurationRepository configurationRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${queue.ai.request}")
    private String aiRequestQueue;

    @Transactional
    public SyntheticReality createReality(SyntheticReality syntheticReality, User user, String artStyle,
            String complexity) {

        syntheticReality.setUser(user);

        RealityConfiguration configuration = syntheticReality.getConfiguration();
        if (configuration != null) {
            configuration = configurationRepository.save(configuration);
            syntheticReality.setConfiguration(configuration);
        }

        SyntheticReality savedReality = realityRepository.save(syntheticReality);

        if (savedReality.getId() != null && configuration != null) {
            MessageQueueRequest messageQueueRequest = new MessageQueueRequest(
                    savedReality.getId(),
                    configuration.getTheme(),
                    configuration.getEnvironment(),
                    configuration.getPhysics(),
                    configuration.getRules(),
                    configuration.getWorldData(),
                    null, artStyle, complexity);
            rabbitTemplate.convertAndSend(aiRequestQueue, messageQueueRequest);
        }
        return savedReality;
    }

    public Optional<SyntheticReality> findRealityById(Long id) {
        return realityRepository.findById(id);
    }

    public List<SyntheticReality> findAllRealitiesByUser(Long userId) {
        return realityRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteRealityById(Long id) {
        realityRepository.deleteById(id);
    }

    @Transactional
    public SyntheticReality updateReality(SyntheticReality syntheticReality, RealityConfiguration newConfig,
            String userFeedback, String artStyle, String complexity) {

        RealityConfiguration oldConfig = syntheticReality.getConfiguration();
        if (oldConfig != null) {

            oldConfig.setTheme(newConfig.getTheme());
            oldConfig.setEnvironment(newConfig.getEnvironment());
            oldConfig.setPhysics(newConfig.getPhysics());
            oldConfig.setRules(newConfig.getRules());

            oldConfig = configurationRepository.save(oldConfig);

            MessageQueueRequest messageQueueRequest = new MessageQueueRequest(
                    syntheticReality.getId(),
                    oldConfig.getTheme(),
                    oldConfig.getEnvironment(),
                    oldConfig.getPhysics(),
                    oldConfig.getRules(),
                    oldConfig.getWorldData(),
                    userFeedback, artStyle, complexity);
            rabbitTemplate.convertAndSend(aiRequestQueue, messageQueueRequest);
        }
        return realityRepository.save(syntheticReality);
    }
}
