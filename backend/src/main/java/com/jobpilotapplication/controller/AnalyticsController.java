package com.jobpilotapplication.controller;

import com.jobpilotapplication.dto.response.AnalyticsResponse;
import com.jobpilotapplication.dto.response.ApiResponse;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping
    public ResponseEntity<ApiResponse<AnalyticsResponse>> getAnalytics(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success("Analytics fetched",
                analyticsService.getAnalytics(user)));
    }
}
