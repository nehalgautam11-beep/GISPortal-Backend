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

    public FcmToken save(FcmToken token) {
        return repository.save(token);
    }
}