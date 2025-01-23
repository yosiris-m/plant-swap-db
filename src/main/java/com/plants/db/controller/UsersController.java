package com.plants.db.controller;


import com.plants.db.models.Users;
import com.plants.db.repository.UserRepository;
import com.plants.db.service.AuthService;
import com.plants.db.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private UserService userService;

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
            System.out.println("users: " + users.getUsername() + ", Usuario: " + users.getUser_password());
        }
        return user;
    }

    @PostMapping
    public ResponseEntity<Users> addUser(@RequestBody Users user) {
        // Encripta la contraseña antes de guardar
        String encryptedPassword = passwordEncoder.encode(user.getUser_password());
        user.setUser_password(encryptedPassword);

        Users savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
