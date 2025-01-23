package com.plants.db.controller;


import com.plants.db.models.UpdatePasswordRequest;
import com.plants.db.models.Users;
import com.plants.db.repository.UserRepository;
import com.plants.db.service.AuthService;
import com.plants.db.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final UserService userService;

    public UsersController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthService authService, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping
    public List<Users> getUser() {
        List<Users> user = userRepository.findAll();
        for (Users users : user) {
            System.out.println("users: " + users.getUsername() + ", Usuario: " + users.getUserPassword());
        }
        return user;
    }

    @PostMapping
    public ResponseEntity<Users> addUser(@RequestBody Users user) {
        // Encripta la contraseña antes de guardar
        String encryptedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encryptedPassword);

        Users savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updatePassword(
            @PathVariable int userId,
            @RequestBody UpdatePasswordRequest request) {
        return userService.updatePassword(userId, request);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserAccount(@PathVariable int userId) {

        //verifico si el usuario existe
        Optional<Users> usersOptional = userRepository.findById(userId);
        if(usersOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        userRepository.delete(usersOptional.get());
        return ResponseEntity.ok("Cuenta de usuario y plantas asociadas eliminadas correctament");
    }
}
