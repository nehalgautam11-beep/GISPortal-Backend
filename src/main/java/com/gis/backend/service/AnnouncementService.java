package com.gis.backend.service;

import com.gis.backend.entity.Announcement;
import com.gis.backend.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    private final AnnouncementRepository repository;
    private final NotificationBroadcastService notificationService;

    public AnnouncementService(
            AnnouncementRepository repository,
            NotificationBroadcastService notificationService
    ) {

        this.repository = repository;
        this.notificationService = notificationService;
    }

    public Announcement createAnnouncement(
            Announcement announcement
    ) {

        announcement.setCreatedAt(
                LocalDateTime.now()
        );

        Announcement saved =
                repository.save(
                        announcement
                );

        try {

            notificationService.broadcastAnnouncement(
                    saved
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        return saved;
    }

    public List<Announcement> getAllAnnouncements() {

        return repository.findAll();
    }

    public void deleteAnnouncement(
            Long id
    ) {

        repository.deleteById(
                id
        );
    }

    public List<Announcement> getAnnouncementsForUser(
            String role,
            String visibleClass
    ) {

        return repository.findAll()
                .stream()
                .filter(a ->
                        isVisibleToRole(
                                a,
                                role
                        )
                )
                .filter(a ->
                        isVisibleToClass(
                                a,
                                visibleClass
                        )
                )
                .collect(
                        Collectors.toList()
                );
    }

    private boolean isVisibleToRole(
            Announcement a,
            String role
    ) {

        String target =
                a.getTargetRole();

        if (
                target == null
                        || target.isBlank()
                        || target.equalsIgnoreCase("ALL")
        ) {

            return true;
        }

        return target.equalsIgnoreCase(
                role
        );
    }

    private boolean isVisibleToClass(
            Announcement a,
            String className
    ) {

        String targets =
                a.getTargetClasses();

        if (
                targets == null
                        || targets.isBlank()
                        || targets.equalsIgnoreCase("ALL")
        ) {

            return true;
        }

        if (
                className == null
                        || className.isBlank()
        ) {

            return true;
        }

        return Arrays.stream(
                        targets.split(",")
                )
                .map(String::trim)
                .anyMatch(c ->
                        c.equalsIgnoreCase(
                                className
                        )
                );
    }
}