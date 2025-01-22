package com.pragadeesh.sros.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiRequest {

    private String theme;
    private String environment;
    private String physics;
    private String rules;
    private String worldData;
    private String userFeedback;
}
