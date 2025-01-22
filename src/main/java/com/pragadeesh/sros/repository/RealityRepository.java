package com.pragadeesh.sros.repository;

import com.pragadeesh.sros.entity.SyntheticReality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealityRepository extends JpaRepository<SyntheticReality, Long> {

    List<SyntheticReality> findByUserId(long userId);
}
