package com.gis.backend.service;

import com.gis.backend.entity.FcmToken;
import com.gis.backend.repository.FcmTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class FcmTokenService {

    private final FcmTokenRepository repository;

    public FcmTokenService(FcmTokenRepository repository) {
        this.repository = repository;
    }

    public FcmToken save(FcmToken request) {

        FcmToken token = repository
                .findByUsername(request.getUsername())
                .orElse(new FcmToken());

        token.setUsername(request.getUsername());
        token.setRole(request.getRole());
        token.setToken(request.getToken());

        return repository.save(token);
    }
}