package com.plants.db.service;

import com.plants.db.models.UpdatePasswordRequest;
import com.plants.db.models.Users;
import com.plants.db.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> updatePassword(int userId, UpdatePasswordRequest request) {
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Users user = userOptional.get();

        // Verificar que el username coincida
        if (!user.getUsername().equals(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para actualizar esta contraseña");
        }

        // Validar la nueva contraseña
        String newPassword = request.getNewPassword();
        if (newPassword == null || newPassword.length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La contraseña debe tener al menos 8 caracteres");
        }

        // Encriptar y guardar la nueva contraseña
        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setUser_password(encryptedPassword);
        userRepository.save(user);

        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }
}
