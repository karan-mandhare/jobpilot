package com.jobpilotapplication.dto.response;

import com.jobpilotapplication.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String profilePic;
    private LocalDateTime createdAt;
    private int totalApplications;
}
