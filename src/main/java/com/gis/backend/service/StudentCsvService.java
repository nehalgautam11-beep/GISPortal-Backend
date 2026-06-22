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

        System.out.println("CSV UPLOAD STARTED");

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
                    (line = reader.readLine()) != null
            ) {

                if (
                        line.trim().isEmpty()
                ) {
                    continue;
                }

                System.out.println("LINE = " + line);

                if (
                        firstRow
                ) {

                    firstRow = false;

                    System.out.println(
                            "HEADER SKIPPED"
                    );

                    continue;
                }

                String[] data =
                        line.split(",", -1);


                System.out.println("CSV API HIT");

                System.out.println(
                        "COLUMNS = "
                                + data.length
                );

                if (
                        data.length != 13
                ) {

                    System.out.println(
                            "INVALID ROW -> "
                                    + line
                    );

                    continue;
                }

                Student student =
                        new Student();

                student.setName(
                        data[0].trim()
                );

                student.setClassName(
                        data[1].trim()
                );

                student.setRollNo(
                        data[2].trim()
                );

                student.setUsername(
                        data[3].trim()
                );

                student.setPassword(
                        data[4].trim()
                );

                student.setFatherName(
                        data[5].trim()
                );

                student.setMotherName(
                        data[6].trim()
                );

                student.setDob(
                        data[7].trim()
                );

                student.setPhone(
                        data[8].trim()
                );

                student.setAddress(
                        data[9].trim()
                );

                student.setAadharNo(
                        data[10].trim()
                );

                student.setSsmid(
                        data[11].trim()
                );

                student.setPenNo(
                        data[12].trim()
                );

                repository.save(student);

                System.out.println(
                        "SAVED = "
                                + student.getName()
                );

                count++;
            }

        } catch (
                Exception e
        ) {

            System.out.println(
                    "CSV IMPORT ERROR"
            );

            e.printStackTrace();
        }

        System.out.println(
                "TOTAL IMPORTED = "
                        + count
        );

        return count;
    }
}