package com.jobpilotapplication.service;

import com.jobpilotapplication.dto.request.LoginRequest;
import com.jobpilotapplication.dto.request.RegisterRequest;
import com.jobpilotapplication.dto.response.AuthResponse;
import com.jobpilotapplication.dto.response.UserResponse;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.enums.Role;
import com.jobpilotapplication.exception.BadRequestException;
import com.jobpilotapplication.repository.JobApplicationRepository;
import com.jobpilotapplication.repository.UserRepository;
import com.jobpilotapplication.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JobApplicationRepository applicationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .isActive(true)
                .build();

        userRepository.save(user);
        emailService.sendWelcomeEmail(user.getEmail(), user.getName());

        String token = jwtUtil.generateToken(user);
        return buildAuthResponse(token, user);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("User not found"));

        String token = jwtUtil.generateToken(user);
        return buildAuthResponse(token, user);
    }

    public UserResponse getCurrentUser(User user) {
        long totalApps = applicationRepository.countByUser(user);
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .profilePic(user.getProfilePic())
                .createdAt(user.getCreatedAt())
                .totalApplications((int) totalApps)
                .build();
    }

    private AuthResponse buildAuthResponse(String token, User user) {
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
