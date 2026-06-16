package com.gis.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/document-files")
public class UploadDocumentController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(

            @RequestParam("file")
            MultipartFile file

    ) {

        try {

            Path uploadPath =
                    Paths.get(
                            "src/main/resources/documents"
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