package com.pragadeesh.sros.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reality_configuration")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealityConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String theme;
    private String environment;
    private String physics;
    private String rules;
    @Lob
    private String worldData;
}
