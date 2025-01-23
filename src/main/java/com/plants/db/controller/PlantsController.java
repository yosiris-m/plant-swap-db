package com.plants.db.controller;

import com.plants.db.models.Plants;
import com.plants.db.repository.PlantaRepository;
import com.plants.db.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
public class PlantsController {

    private final PlantaRepository plantaRepository;
    private final UserRepository userRepository;

    public PlantsController(PlantaRepository plantaRepository, UserRepository userRepository) {
        this.plantaRepository = plantaRepository;
        this.userRepository = userRepository;
    }

    //http://localhost:8080/api/plants
    @GetMapping
    public List<Plants> getPlants() {
        List<Plants> plants = plantaRepository.findAll();
        for (Plants plant : plants) {
            System.out.println("Planta: " + plant.getName_plant() + ", Descripción: " + plant.getDescription_plant());
        }
        return plants;
    }
}
