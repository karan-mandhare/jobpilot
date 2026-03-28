package com.jobpilotapplication.service;

import com.jobpilotapplication.dto.response.ResumeResponse;
import com.jobpilotapplication.entity.Resume;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.exception.BadRequestException;
import com.jobpilotapplication.exception.ResourceNotFoundException;
import com.jobpilotapplication.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final CloudinaryService cloudinaryService;

    public List<ResumeResponse> getAllResumes(User user) {
        return resumeRepository.findByUserOrderByUploadedAtDesc(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResumeResponse uploadResume(User user, MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
            throw new BadRequestException("Only PDF files are allowed");
        }

        Map uploadResult = cloudinaryService.uploadFile(file, "resumes");
        String fileUrl = (String) uploadResult.get("secure_url");
        String publicId = (String) uploadResult.get("public_id");

        // Check if it's the first resume (make it primary)
        boolean isFirst = resumeRepository.findByUserOrderByUploadedAtDesc(user).isEmpty();

        Resume resume = Resume.builder()
                .user(user)
                .fileName(file.getOriginalFilename())
                .fileUrl(fileUrl)
                .publicId(publicId)
                .isPrimary(isFirst)
                .build();

        resumeRepository.save(resume);
        return mapToResponse(resume);
    }

    @Transactional
    public ResumeResponse setPrimary(User user, Long id) {
        Resume resume = resumeRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

        // Unset previous primary
        resumeRepository.findByUserAndIsPrimaryTrue(user)
                .ifPresent(r -> {
                    r.setIsPrimary(false);
                    resumeRepository.save(r);
                });

        resume.setIsPrimary(true);
        resumeRepository.save(resume);
        return mapToResponse(resume);
    }

    @Transactional
    public void deleteResume(User user, Long id) {
        Resume resume = resumeRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));
        cloudinaryService.deleteFile(resume.getPublicId());
        resumeRepository.delete(resume);
    }

    @Transactional
    public ResumeResponse saveAiFeedback(User user, Long id, String feedback) {
        Resume resume = resumeRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));
        resume.setAiFeedback(feedback);
        resumeRepository.save(resume);
        return mapToResponse(resume);
    }

    private ResumeResponse mapToResponse(Resume resume) {
        return ResumeResponse.builder()
                .id(resume.getId())
                .fileName(resume.getFileName())
                .fileUrl(resume.getFileUrl())
                .isPrimary(resume.getIsPrimary())
                .aiFeedback(resume.getAiFeedback())
                .uploadedAt(resume.getUploadedAt())
                .build();
    }
}