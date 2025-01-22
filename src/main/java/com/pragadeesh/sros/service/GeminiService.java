package com.pragadeesh.sros.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.Content;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.Part;
import com.google.cloud.vertexai.generativeai.preview.GenerativeModel;
import com.pragadeesh.sros.dto.MessageQueueRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class GeminiService implements LLMService {

    @Value("${gemini.project.id}")
    private String projectId;

    @Value("${gemini.region}")
    private String region;

    @Value("${gemini.model.name}")
    private String modelName;

    @Value("${gemini.service.account.key}")
    private String serviceAccountKey;

    private GenerativeModel model;

    private GenerativeModel getModel() throws IOException {
        if (model == null) {
            try {
                String unescapedJson = serviceAccountKey.replace("\\n", "\n");

                InputStream credentialsStream = new ByteArrayInputStream(
                        unescapedJson.getBytes(StandardCharsets.UTF_8));

                GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                        .createScoped("https://www.googleapis.com/auth/cloud-platform");

                VertexAI vertexAI = new VertexAI(projectId, region, credentials);

                model = new GenerativeModel(modelName, vertexAI);

                log.info("Successfully initialized Gemini model with project Id: {} in region: {}", projectId, region);
            } catch (IOException e) {
                log.error("Failed to initialize Gemini model. Error: {}", e.getMessage());
                throw new RuntimeException("Failed to initialize gemini model", e);
            }
        }
        return model;
    }

    @Override
    public String generateWorldData(MessageQueueRequest request) {
        try {
            String prompt = String.format(
                    "Generate the world data for a synthetic reality with the following theme: '%s' , environment: '%s', physics: '%s' and rules: '%s'. Please provide the response as a valid JSON with a 'worldData' field. Make sure that the JSON is valid",
                    request.getTheme(), request.getEnvironment(), request.getPhysics(), request.getRules());

            GenerativeModel model = getModel();
            GenerateContentResponse response = model.generateContent(prompt);

            JSONObject jsonResponse = new JSONObject();

            if (response != null && !response.getCandidatesList().isEmpty()) {
                Content content = response.getCandidates(0).getContent();
                if (content != null && !content.getPartsList().isEmpty()) {
                    StringBuilder generatedText = new StringBuilder();

                    for (Part part : content.getPartsList()) {
                        if (part.hasText()) {
                            generatedText.append(part.getText());
                        }
                    }
                    jsonResponse.put("WorldData", generatedText.toString());
                } else {
                    log.warn("No content parts generated from the model");
                    jsonResponse.put("WorldData", "");
                }
            } else {
                log.warn("No content generated from the model");
                jsonResponse.put("WorldData", "");
            }
            return jsonResponse.toString();
        } catch (IOException e) {
            log.error("Error with Gemini API request: {}", e.getMessage());
            throw new RuntimeException("Failed to generate world data using vertex AI: " + e.getMessage(), e);
        }
    }
}
