package com.gis.backend.service;

import com.gis.backend.entity.User;
import com.gis.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository repository,
            PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(
            String username,
            String password
    ) {

        User user =
                repository.findByUsername(
                        username
                ).orElseThrow(
                        () -> new RuntimeException(
                                "User Not Found"
                        )
                );

        boolean matches;

        if (user.getPassword().startsWith("$2a$")) {

            matches = passwordEncoder.matches(
                    password,
                    user.getPassword()
            );

        } else {

            matches = user.getPassword().equals(password);
        }

        if (!matches) {

            throw new RuntimeException(
                    "Invalid Password"
            );
        }

        return user;
    }

    public void changePassword(
            String username,
            String oldPassword,
            String newPassword
    ) {

        User user =
                repository.findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        boolean matches;

        if (user.getPassword().startsWith("$2a$")) {

            matches = passwordEncoder.matches(
                    oldPassword,
                    user.getPassword()
            );

        } else {

            matches = user.getPassword().equals(oldPassword);
        }

        if (!matches) {

            throw new RuntimeException(
                    "Incorrect current password"
            );
        }

        user.setPassword(
                passwordEncoder.encode(newPassword)
        );

        repository.save(user);
    }
}