package com.pragadeesh.sros.controller;

import com.pragadeesh.sros.entity.RealityConfiguration;
import com.pragadeesh.sros.entity.SyntheticReality;
import com.pragadeesh.sros.entity.User;
import com.pragadeesh.sros.service.RealityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/realities")
public class RealityController {

    private final RealityService realityService;

    @PostMapping
    public ResponseEntity<?> createReality(@Valid @RequestBody SyntheticReality syntheticReality,
            Authentication authentication, @RequestParam(required = false) String artStyle,
            @RequestParam(required = false) String complexity) {
        try {
            User user = (User) authentication.getPrincipal();
            SyntheticReality createdReality = realityService.createReality(syntheticReality, user, artStyle,
                    complexity);
            return new ResponseEntity<>(createdReality, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findRealityById(@PathVariable Long id) {
        Optional<SyntheticReality> reality = realityService.findRealityById(id);
        return reality.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SyntheticReality>> findAllRealitiesByUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(realityService.findAllRealitiesByUser(user.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReality(@PathVariable Long id, @RequestBody RealityConfiguration newConfig,
            @RequestParam(required = false) String userFeedback, @RequestParam(required = false) String artStyle,
            @RequestParam(required = false) String complexity) {
        try {
            Optional<SyntheticReality> reality = realityService.findRealityById(id);
            if (reality.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            SyntheticReality updatedReality = realityService.updateReality(reality.get(), newConfig, userFeedback,
                    artStyle, complexity);
            return ResponseEntity.ok(updatedReality);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReality(@PathVariable Long id) {
        realityService.deleteRealityById(id);
        return ResponseEntity.ok("Successfully Deleted");
    }
}
