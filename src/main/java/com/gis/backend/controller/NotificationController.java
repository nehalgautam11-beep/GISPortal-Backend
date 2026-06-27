package com.gis.backend.controller;

import com.gis.backend.entity.FcmToken;
import com.gis.backend.repository.FcmTokenRepository;
import com.gis.backend.service.FirebaseNotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private final FirebaseNotificationService firebaseNotificationService;
    private final FcmTokenRepository repository;

    public NotificationController(
            FirebaseNotificationService firebaseNotificationService,
            FcmTokenRepository repository
    ) {
        this.firebaseNotificationService = firebaseNotificationService;
        this.repository = repository;
    }

    @GetMapping("/api/notifications/test")
    public String testNotification() throws Exception {

        FcmToken token = repository.findAll().stream().findFirst().orElseThrow();

        String response = firebaseNotificationService.sendNotification(
                token.getToken(),
                "Global Innovative School",
                "🎉 Congratulations! Your first push notification is working."
        );

        return response;
    }
}