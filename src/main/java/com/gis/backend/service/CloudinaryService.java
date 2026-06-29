package com.gis.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(
            Cloudinary cloudinary
    ) {
        this.cloudinary = cloudinary;
    }

    public String uploadPdf(
            MultipartFile file
    ) throws Exception {

        Map<?, ?> result =
                cloudinary.uploader().upload(
                        file.getBytes(),
                        ObjectUtils.asMap(
                                "resource_type", "raw",
                                "folder", "gis-pdfs"
                        )
                );

        return result.get("secure_url").toString();
    }
}