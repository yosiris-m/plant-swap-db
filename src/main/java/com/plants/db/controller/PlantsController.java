package com.plants.db.controller;

import com.plants.db.models.Plants;
import com.plants.db.models.Users;
import com.plants.db.repository.PlantaRepository;
import com.plants.db.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //http://localhost:8080/api/plants/create?userId=3
    @PostMapping("/create")
    public ResponseEntity<Plants> createPlant(@RequestBody Plants plant, @RequestParam int userId) {
        Users user = userRepository.findById(userId).orElseThrow(()->  new RuntimeException("El usuario no se ha encontrado"));

        plant.setUser(user);
        Plants savedPlant = plantaRepository.save(plant);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPlant);
    }

}
