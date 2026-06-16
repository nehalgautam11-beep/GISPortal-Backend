package com.gis.backend.service;

import com.gis.backend.entity.Role;
import com.gis.backend.entity.Student;
import com.gis.backend.entity.User;
import com.gis.backend.repository.StudentRepository;
import com.gis.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(
            StudentRepository repository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {

        this.repository = repository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Student saveStudent(
            Student student
    ) {

        Student saved =
                repository.save(student);

        if (
                student.getUsername() != null
                        &&
                        !student.getUsername().isBlank()
                        &&
                        userRepository
                                .findByUsername(
                                        student.getUsername()
                                )
                                .isEmpty()
        ) {

            User user = new User();

            user.setUsername(
                    student.getUsername()
            );

            String rawPassword =
                    (student.getPassword() != null
                            && !student.getPassword().isBlank())
                            ? student.getPassword()
                            : "password123";

            user.setPassword(
                    passwordEncoder.encode(rawPassword)
            );

            user.setRole(Role.STUDENT_PARENT);

            userRepository.save(user);
        }

        return saved;
    }


    public List<Student> searchStudents(
            String name
    ) {

        return repository
                .findByNameContainingIgnoreCase(
                        name
                );
    }



    public List<Student> getStudents() {

        return repository.findAll();
    }

    public Student getStudentById(
            Long id
    ) {

        return repository.findById(id)
                .orElseThrow();
    }

    public Student getStudentByUsername(
            String username
    ) {

        return repository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found"
                        )
                );
    }

    public Student updateStudent(
            Long id,
            Student updatedStudent
    ) {

        Student student =
                repository.findById(id)
                        .orElseThrow();

        student.setName(
                updatedStudent.getName()
        );

        student.setFatherName(
                updatedStudent.getFatherName()
        );

        student.setMotherName(
                updatedStudent.getMotherName()
        );

        student.setDob(
                updatedStudent.getDob()
        );

        student.setRollNo(
                updatedStudent.getRollNo()
        );

        student.setClassName(
                updatedStudent.getClassName()
        );

        student.setAddress(
                updatedStudent.getAddress()
        );

        student.setAadharNo(
                updatedStudent.getAadharNo()
        );

        student.setPhone(
                updatedStudent.getPhone()
        );

        student.setClassTeacher(
                updatedStudent.getClassTeacher()
        );

        return repository.save(
                student
        );
    }

    public void deleteStudent(
            Long id
    ) {

        repository.findById(id).ifPresent(student -> {

            if (
                    student.getUsername() != null
                            && !student.getUsername().isBlank()
            ) {

                userRepository
                        .findByUsername(student.getUsername())
                        .ifPresent(userRepository::delete);
            }
        });

        repository.deleteById(id);
    }

    public List<Student> getStudentsByClass(
            String className
    ) {

        return repository.findByClassName(
                className
        );
    }
}