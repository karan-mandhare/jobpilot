package com.jobpilotapplication.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiResponse {
    private String result;
    private String sessionType;
    private Integer tokensUsed;
}