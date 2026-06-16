package com.gis.backend.controller;

import com.gis.backend.entity.Teacher;
import com.gis.backend.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService service;

    public TeacherController(
            TeacherService service
    ) {
        this.service = service;
    }


    @GetMapping("/search")
    public List<Teacher> searchTeachers(

            @RequestParam
            String name
    ) {

        return service.searchTeachers(
                name
        );
    }


    @PutMapping("/{id}")
    public Teacher updateTeacher(

            @PathVariable
            Long id,

            @RequestBody
            Teacher teacher
    ) {

        return service.updateTeacher(
                id,
                teacher
        );
    }




    @GetMapping("/{id}")
    public Teacher getTeacherById(
            @PathVariable Long id
    ) {
        return service.getTeacherById(id);
    }

    @PostMapping
    public Teacher saveTeacher(
            @RequestBody Teacher teacher
    ) {
        return service.saveTeacher(
                teacher
        );
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(
            @PathVariable Long id
    ) {
        service.deleteTeacher(id);
    }

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return service.getAllTeachers();
    }

    @GetMapping("/by-username")
    public Teacher getTeacherByUsername(
            @RequestParam String username
    ) {

        return service.getTeacherByUsername(
                username
        );
    }
}