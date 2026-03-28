package com.jobpilotapplication.controller;

import com.jobpilotapplication.dto.response.ApiResponse;
import com.jobpilotapplication.entity.Notification;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Notification>>> getAll(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success("Notifications fetched",
                notificationService.getAll(user)));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getUnreadCount(
            @AuthenticationPrincipal User user) {
        long count = notificationService.getUnreadCount(user);
        return ResponseEntity.ok(ApiResponse.success("Unread count fetched",
                Map.of("count", count)));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        notificationService.markAsRead(user, id);
        return ResponseEntity.ok(ApiResponse.success("Marked as read", null));
    }

    @PatchMapping("/read-all")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead(
            @AuthenticationPrincipal User user) {
        notificationService.markAllAsRead(user);
        return ResponseEntity.ok(ApiResponse.success("All marked as read", null));
    }
}
