package com.gis.backend.controller;

import com.gis.backend.entity.Student;
import com.gis.backend.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(
            StudentService service
    ) {

        this.service = service;
    }

    @PostMapping
    public Student saveStudent(
            @RequestBody Student student
    ) {

        return service.saveStudent(
                student
        );
    }


    @GetMapping("/search")
    public List<Student> searchStudents(

            @RequestParam
            String name

    ) {

        return service.searchStudents(
                name
        );
    }


    @GetMapping
    public List<Student> getStudents() {

        return service.getStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(
            @PathVariable Long id
    ) {

        return service.getStudentById(
                id
        );
    }



    @PutMapping("/{id}")
    public Student updateStudent(

            @PathVariable
            Long id,

            @RequestBody
            Student student
    ) {

        return service.updateStudent(
                id,
                student
        );
    }



    @PostMapping("/{id}/photo")
    public Student updatePhoto(

            @PathVariable
            Long id,

            @RequestParam
            String photoUrl
    ) {

        Student student =
                service.getStudentById(id);

        student.setPhotoUrl(
                photoUrl
        );

        return service.saveStudent(
                student
        );
    }






    @DeleteMapping("/{id}")
    public void deleteStudent(
            @PathVariable Long id
    ) {

        service.deleteStudent(
                id
        );
    }






    @GetMapping("/class")
    public List<Student> getStudentsByClass(

            @RequestParam
            String className
    ) {

        return service.getStudentsByClass(
                className
        );
    }

    @GetMapping("/by-username")
    public Student getStudentByUsername(
            @RequestParam String username
    ) {

        return service.getStudentByUsername(
                username
        );
    }
}