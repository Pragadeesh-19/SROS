package com.pragadeesh.sros.service;

import com.pragadeesh.sros.dto.AiRequest;
import com.pragadeesh.sros.dto.AiResponse;
import org.springframework.stereotype.Service;

@Service
public class AiServiceImpl implements AiService {

    @Override
    public AiResponse generateWorldData(AiRequest aiRequest) {
        // mock implementation
        String worldData = "Generated world data based on " + aiRequest.getTheme() +
                ", " + aiRequest.getEnvironment() + ", " + aiRequest.getPhysics() +
                " and " + aiRequest.getRules();

        return new AiResponse(worldData, "SUCCESS", "World data generated successfully.");
    }

    @Override
    public AiResponse updateWorldData(AiRequest aiRequest) {

        String updatedWorldData = "Updated world data based on " + aiRequest.getTheme()+
                ", " + aiRequest.getEnvironment()+", " + aiRequest.getPhysics()+
                ", " + aiRequest.getRules() + " and user feedback: " + aiRequest.getUserFeedback();
        return new AiResponse(updatedWorldData, "SUCCESS", "World data updated successfully");
    }
}
