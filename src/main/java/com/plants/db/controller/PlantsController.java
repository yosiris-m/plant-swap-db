package com.plants.db.controller;

import com.plants.db.models.Plants;
import com.plants.db.models.Users;
import com.plants.db.repository.PlantaRepository;
import com.plants.db.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    //http://localhost:8080/api/plants/1?userId=1
    @PutMapping("/{id}")
    public ResponseEntity<Plants> updatePlant(
            @PathVariable Long id,
            @RequestBody Plants plant,
            @RequestParam int userId) {

        // Verificar si el usuario existe
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("El usuario no se ha encontrado"));

        // Verificar si la planta existe
        Optional<Plants> isExistingPlant = plantaRepository.findById(id);
        if (isExistingPlant.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Planta no encontrada
        }

        Plants existingPlant = isExistingPlant.get();

        // Verificar si la planta pertenece al usuario
        if (!Integer.valueOf(existingPlant.getUser().getId()).equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // La planta no pertenece al usuario
        }

        // Actualizar solo los campos permitidos
        existingPlant.setName_plant(plant.getName_plant());
        existingPlant.setDescription_plant(plant.getDescription_plant());
        existingPlant.setLocation(plant.getLocation());

        // Guardar los cambios
        Plants updatedPlant = plantaRepository.save(existingPlant);
        return ResponseEntity.ok(updatedPlant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Plants> deletePlant(@PathVariable Long id) {
        Optional<Plants> isExistingPlant = plantaRepository.findById(id);
        if (isExistingPlant.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Plants existingPlant = isExistingPlant.get();
        plantaRepository.delete(existingPlant);
        return ResponseEntity.ok(existingPlant);
    }

}
