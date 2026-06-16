package com.gis.backend.service;

import com.gis.backend.entity.Student;
import com.gis.backend.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class StudentCsvService {

    private final StudentRepository repository;

    public StudentCsvService(
            StudentRepository repository
    ) {

        this.repository = repository;
    }

    public int uploadCsv(
            MultipartFile file
    ) {

        int count = 0;

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    file.getInputStream()
                            )
                    );

            String line;

            boolean firstRow = true;

            while (
                    (line = reader.readLine())
                            != null
            ) {

                if (
                        firstRow
                ) {

                    firstRow = false;
                    continue;
                }

                String[] data =
                        line.split(",");

                Student student =
                        new Student();

                student.setName(
                        data[0]
                );

                student.setFatherName(
                        data[1]
                );

                student.setMotherName(
                        data[2]
                );

                student.setDob(
                        data[3]
                );

                student.setRollNo(
                        data[4]
                );

                student.setClassName(
                        data[5]
                );

                student.setAddress(
                        data[6]
                );

                student.setAadharNo(
                        data[7]
                );

                student.setPhone(
                        data[8]
                );

                student.setSsmid(
                        data[9]
                );

                student.setPenNo(
                        data[10]
                );

                repository.save(
                        student
                );

                count++;
            }

        } catch (
                Exception e
        ) {

            e.printStackTrace();
        }

        return count;
    }
}