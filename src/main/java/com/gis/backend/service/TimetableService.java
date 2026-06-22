package com.gis.backend.service;

import com.gis.backend.entity.Timetable;
import com.gis.backend.repository.TimetableRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class TimetableService {

    private final TimetableRepository repository;

    public TimetableService(
            TimetableRepository repository
    ) {

        this.repository = repository;
    }

    public Timetable saveTimetable(
            Timetable timetable
    ) {

        return repository.save(
                timetable
        );
    }

    public List<Timetable> getAllTimetables() {

        return repository.findAll();
    }

    public List<Timetable> getTimetablesForClass(
            String className
    ) {

        return repository.findAll().stream()
                .filter(t -> isVisibleToClass(t, className))
                .collect(Collectors.toList());
    }

    public List<Timetable> getTimetablesForTeacher(
            String teacherName,
            String assignedClasses
    ) {

        List<String> teacherClasses = assignedClasses == null
                ? List.of()
                : Arrays.stream(assignedClasses.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());

        return repository.findAll().stream()
                .filter(t ->

                        // Explicit teacher timetable
                        teacherName.equalsIgnoreCase(
                                t.getTeacherName()
                        )

                                ||

                                // Teachers-only timetable
                                "TEACHER".equalsIgnoreCase(
                                        t.getType()
                                )

                                ||

                                // Class timetable for teacher's classes
                                teacherClasses.stream()
                                        .anyMatch(cls ->
                                                isVisibleToClass(
                                                        t,
                                                        cls
                                                )
                                        )
                )
                .collect(Collectors.toList());
    }

    public List<Timetable> getClassTimetable(
            String className
    ) {

        return getTimetablesForClass(
                className
        );
    }

    public List<Timetable> getTeacherTimetable(
            String teacherName
    ) {

        return repository.findByTeacherName(
                teacherName
        );
    }

    private boolean isVisibleToClass(
            Timetable t,
            String className
    )
    {

        // Teachers-only timetable should never be visible to students
        if (
                "TEACHER".equalsIgnoreCase(
                        t.getType()
                )
        ) {
            return false;
        }

        String targets = t.getTargetClasses();

        if (
                targets == null
                        || targets.isBlank()
                        || targets.equalsIgnoreCase("ALL")
        ) {
            return true;
        }

        return Arrays.stream(
                        targets.split(",")
                )
                .map(String::trim)
                .anyMatch(c ->
                        c.equalsIgnoreCase(className)
                );
    }

    public void deleteTimetable(
            Long id
    ) {

        repository.deleteById(id);
    }
}