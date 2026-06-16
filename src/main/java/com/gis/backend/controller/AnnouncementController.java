package com.gis.backend.controller;

import com.gis.backend.entity.Announcement;
import com.gis.backend.service.AnnouncementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementService service;

    public AnnouncementController(
            AnnouncementService service
    ) {

        this.service = service;
    }

    @PostMapping
    public Announcement createAnnouncement(
            @RequestBody Announcement announcement
    ) {

        return service.createAnnouncement(
                announcement
        );
    }

    @GetMapping
    public List<Announcement> getAnnouncements() {

        return service.getAllAnnouncements();
    }

    @GetMapping("/filtered")
    public List<Announcement> getFiltered(
            @RequestParam String role,
            @RequestParam(
                    required = false,
                    defaultValue = ""
            ) String className
    ) {

        return service.getAnnouncementsForUser(
                role,
                className
        );
    }

    @DeleteMapping("/{id}")
    public void deleteAnnouncement(
            @PathVariable Long id
    ) {

        service.deleteAnnouncement(
                id
        );
    }
}