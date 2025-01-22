package com.pragadeesh.sros.repository;

import com.pragadeesh.sros.entity.RealityConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<RealityConfiguration, Long> {

}
