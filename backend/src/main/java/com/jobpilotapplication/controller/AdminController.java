package com.jobpilotapplication.controller;

import com.jobpilotapplication.dto.response.ApiResponse;
import com.jobpilotapplication.dto.response.UserResponse;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.exception.ResourceNotFoundException;
import com.jobpilotapplication.repository.JobApplicationRepository;
import com.jobpilotapplication.repository.UserRepository;
import com.jobpilotapplication.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final JobApplicationRepository applicationRepository;
    private final AuthService authService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userRepository.findAll()
                .stream()
                .map(user -> authService.getCurrentUser(user))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Users fetched", users));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getStats() {
        long totalUsers = userRepository.count();
        long totalApplications = applicationRepository.count();

        Map<String, Long> stats = Map.of(
                "totalUsers", totalUsers,
                "totalApplications", totalApplications
        );

        return ResponseEntity.ok(ApiResponse.success("Stats fetched", stats));
    }

    @PatchMapping("/users/{id}/toggle-active")
    public ResponseEntity<ApiResponse<Void>> toggleUserActive(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setIsActive(!user.getIsActive());
        userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.success("User status updated", null));
    }
}
