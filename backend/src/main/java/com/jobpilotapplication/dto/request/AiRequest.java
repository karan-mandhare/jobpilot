package com.jobpilotapplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AiRequest {

    @NotBlank(message = "Input is required")
    private String input;

    private String jobRole;
    private String experienceLevel;
}
