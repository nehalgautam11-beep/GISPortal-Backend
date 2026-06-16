package com.gis.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/photos")
public class UploadPhotoController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhoto(

            @RequestParam("file")
            MultipartFile file

    ) {

        try {

            Path uploadPath =
                    Paths.get(
                            "src/main/resources/student_photos"
                    );

            Files.createDirectories(
                    uploadPath
            );

            Path filePath =
                    uploadPath.resolve(
                            file.getOriginalFilename()
                    );

            Files.write(
                    filePath,
                    file.getBytes()
            );

            return ResponseEntity.ok(
                    file.getOriginalFilename()
            );

        } catch (
                Exception e
        ) {

            return ResponseEntity.badRequest()
                    .body(
                            e.getMessage()
                    );
        }
    }
}