package com.gis.backend.controller;

import com.gis.backend.entity.FcmToken;
import com.gis.backend.service.FcmTokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fcm")
public class FcmTokenController {

    private final FcmTokenService service;

    public FcmTokenController(FcmTokenService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public FcmToken register(@RequestBody FcmToken token) {
        return service.save(token);
    }
}