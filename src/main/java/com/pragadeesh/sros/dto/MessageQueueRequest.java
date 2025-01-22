package com.pragadeesh.sros.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageQueueRequest {

    private Long realityId;
    private String theme;
    private String environment;
    private String physics;
    private String rules;
    private String worldData;
    private String userFeedback;
    private String artStyle;
    private String complexity;
}
