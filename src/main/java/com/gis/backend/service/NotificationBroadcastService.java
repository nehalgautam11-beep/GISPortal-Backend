package com.gis.backend.service;

import com.gis.backend.entity.Announcement;
import com.gis.backend.entity.Assignment;
import com.gis.backend.entity.FcmToken;
import com.gis.backend.repository.FcmTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationBroadcastService {

    private final FirebaseNotificationService firebase;
    private final FcmTokenRepository tokenRepository;

    public NotificationBroadcastService(
            FirebaseNotificationService firebase,
            FcmTokenRepository tokenRepository
    ) {
        this.firebase = firebase;
        this.tokenRepository = tokenRepository;
    }

    public void notifyAllUsers(
            String title,
            String body
    ) {

        tokenRepository.findAll()
                .forEach(token -> {

                    try {

                        firebase.sendNotification(
                                token.getToken(),
                                title,
                                body
                        );

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                });
    }

    public void broadcastAnnouncement(Announcement saved) {
    }

    public void broadcastAssignment(Assignment saved) {
    }
}