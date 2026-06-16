package com.gis.backend.service;

import com.gis.backend.entity.Role;
import com.gis.backend.entity.Teacher;
import com.gis.backend.entity.User;
import com.gis.backend.repository.TeacherRepository;
import com.gis.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository repository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TeacherService(
            TeacherRepository repository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {

        this.repository = repository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Teacher saveTeacher(
            Teacher teacher
    ) {

        Teacher saved =
                repository.save(teacher);

        if (
                teacher.getUsername() != null
                        &&
                        !teacher.getUsername().isBlank()
                        &&
                        userRepository
                                .findByUsername(
                                        teacher.getUsername()
                                )
                                .isEmpty()
        ) {

            User user = new User();

            user.setUsername(
                    teacher.getUsername()
            );

            String rawPassword =
                    (teacher.getPassword() != null
                            && !teacher.getPassword().isBlank())
                            ? teacher.getPassword()
                            : "password123";

            user.setPassword(
                    passwordEncoder.encode(rawPassword)
            );

            user.setRole(Role.TEACHER);

            user.setAssignedClasses(
                    teacher.getAssignedClasses()
            );

            userRepository.save(user);
        }

        return saved;
    }

    public Teacher getTeacherById(Long id) {

        return repository.findById(id)
                .orElseThrow();
    }

    public void deleteTeacher(Long id) {

        Teacher teacher =
                repository.findById(id)
                        .orElseThrow();

        if (
                teacher.getUsername() != null
                        &&
                        !teacher.getUsername().isBlank()
        ) {

            userRepository
                    .findByUsername(
                            teacher.getUsername()
                    )
                    .ifPresent(
                            userRepository::delete
                    );
        }

        repository.deleteById(id);
    }


    public List<Teacher> searchTeachers(
            String name
    ) {

        return repository
                .findByNameContainingIgnoreCase(
                        name
                );
    }


    public Teacher updateTeacher(
            Long id,
            Teacher updatedTeacher
    ) {

        Teacher teacher =
                repository.findById(id)
                        .orElseThrow();

        teacher.setName(
                updatedTeacher.getName()
        );

        teacher.setPhone(
                updatedTeacher.getPhone()
        );

        teacher.setEmail(
                updatedTeacher.getEmail()
        );

        teacher.setQualification(
                updatedTeacher.getQualification()
        );

        teacher.setAssignedClasses(
                updatedTeacher.getAssignedClasses()
        );

        teacher.setUsername(
                updatedTeacher.getUsername()
        );

        Teacher saved =
                repository.save(teacher);

        userRepository
                .findByUsername(
                        teacher.getUsername()
                )
                .ifPresent(user -> {

                    user.setAssignedClasses(
                            updatedTeacher.getAssignedClasses()
                    );

                    userRepository.save(user);
                });

        return saved;
    }

    public List<Teacher> getAllTeachers() {

        return repository.findAll();
    }

    public Teacher getTeacherByUsername(
            String username
    ) {

        return repository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Teacher not found"
                        )
                );
    }
}