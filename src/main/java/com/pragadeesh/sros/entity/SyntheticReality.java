package com.pragadeesh.sros.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "synthetic_realities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SyntheticReality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "reality cannot be empty")
    @Size(min = 3, max = 100, message = "Reality name must be between 3 and 100 characters long")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "configuration_id")
    private RealityConfiguration configuration;
}
