package com.jobpilotapplication.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.jobpilotapplication.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
@Slf4j
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map uploadFile(MultipartFile file, String folder) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File is empty or null");
        }

        try {
            // Use getBytes() instead of InputStream
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "resource_type", "raw", // for PDF / non-image
                            "use_filename", true,
                            "unique_filename", true
                    )
            );
            return uploadResult;
        } catch (IOException e) {
            log.error("Error reading file bytes", e);
            throw new BadRequestException("File upload failed: " + e.getMessage());
        } catch (Exception e) {
            log.error("Cloudinary upload failed", e);
            throw new BadRequestException("File upload failed due to Cloudinary error: " + e.getMessage());
        }
    }

    public void deleteFile(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId,
                    ObjectUtils.asMap("resource_type", "raw"));
        } catch (IOException e) {
            log.error("Cloudinary delete failed: {}", e.getMessage());
        }
    }
}