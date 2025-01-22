package com.pragadeesh.sros.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiResponse {

    private String worldData;
    private String status;
    private String message;
}
