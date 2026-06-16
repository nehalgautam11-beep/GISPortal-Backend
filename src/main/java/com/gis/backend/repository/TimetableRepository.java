package com.gis.backend.repository;

import com.gis.backend.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimetableRepository
        extends JpaRepository<Timetable, Long> {

    List<Timetable> findByClassName(
            String className
    );

    List<Timetable> findByTeacherName(
            String teacherName
    );
}