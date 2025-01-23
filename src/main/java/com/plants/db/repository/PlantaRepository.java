package com.plants.db.repository;

import com.plants.db.models.Plants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantaRepository extends JpaRepository<Plants, Long> {
    Plants findByname_plant(String name);
}
