package com.gis.backend.controller;

import com.gis.backend.dto.LoginRequestDto;
import com.gis.backend.dto.LoginResponseDto;
import com.gis.backend.entity.User;
import com.gis.backend.security.JwtService;
import com.gis.backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authService;

    public AuthController(
            JwtService jwtService,
            AuthService authService
    ) {

        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequestDto request
    ) {

        try {

            User user =
                    authService.login(
                            request.username,
                            request.password
                    );

            System.out.println(
                    "LOGIN USER = "
                            + user.getUsername()
                            + " ROLE = "
                            + user.getRole()
            );

            String token =
                    jwtService.generateToken(
                            user.getUsername()
                    );

            return ResponseEntity.ok(
                    new LoginResponseDto(
                            token,
                            user.getRole().name(),
                            user.getUsername(),
                            user.getAssignedClasses()
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestParam String username,
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ) {

        try {

            authService.changePassword(
                    username,
                    oldPassword,
                    newPassword
            );

            return ResponseEntity.ok(
                    "{\"message\": \"Password changed successfully\"}"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}