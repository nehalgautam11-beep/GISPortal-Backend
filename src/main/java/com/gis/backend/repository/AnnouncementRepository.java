package com.gis.backend.repository;

import com.gis.backend.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository
        extends JpaRepository<Announcement, Long> {
}