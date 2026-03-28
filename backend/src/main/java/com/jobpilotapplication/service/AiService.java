package com.jobpilotapplication.service;

import com.jobpilotapplication.dto.response.AiResponse;
import com.jobpilotapplication.entity.AiSession;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.repository.AiSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiService {

    @Value("${app.ai.api-key}")
    private String apiKey;

    @Value("${app.ai.api-url}")
    private String apiUrl;

    @Value("${app.ai.model}")
    private String model;

    private final RestTemplate restTemplate;
    private final AiSessionRepository aiSessionRepository;

    public AiResponse reviewResume(User user, String resumeText) {
        if (resumeText == null || resumeText.isEmpty()) {
            // handle empty resume input
            return AiResponse.builder()
                    .result("Resume content is empty. Please provide a valid resume text.")
                    .sessionType("RESUME_REVIEW")
                    .build();
        }

        try {
            String prompt = """
                You are an expert resume reviewer for software engineering roles in India.
                Review the following resume and provide structured feedback:
                
                1. **Overall Score**: X/10
                2. **Top 3 Strengths**
                3. **Top 3 Areas for Improvement**
                4. **Missing ATS Keywords** (for software engineering roles)
                5. **Formatting Suggestions**
                6. **Action Items** (3 quick wins to improve the resume)
                
                Resume Content:
                """ + resumeText;

            String response = callClaudeApi(prompt);  // your AI API call
            saveSession(user, "RESUME_REVIEW", resumeText, response, 0);

            return AiResponse.builder()
                    .result(response)
                    .sessionType("RESUME_REVIEW")
                    .build();

        } catch (Exception e) {
            // catch any other unexpected exception
            log.error("Unexpected error during resume review", e);
            return AiResponse.builder()
                    .result("An unexpected error occurred while reviewing the resume. Please try again later.")
                    .sessionType("RESUME_REVIEW")
                    .build();
        }
    }

    public AiResponse generateInterviewQuestions(User user, String jobRole, String experienceLevel) {
        String prompt = String.format("""
                Generate structured interview questions for a %s role with %s experience in India.
                
                Format your response as follows:
                
                ## Technical Questions (5 questions)
                (Core technical concepts for this role)
                
                ## DSA / Problem Solving (5 questions)
                (Data structures and algorithm questions)
                
                ## System Design (3 questions)
                (System design scenarios relevant to this role)
                
                ## HR / Behavioral (2 questions)
                (Common HR questions)
                
                For each question, provide:
                - The question
                - Expected answer / key points to cover
                - Difficulty: Easy / Medium / Hard
                """, jobRole, experienceLevel);

        String response = callClaudeApi(prompt);
        saveSession(user, "INTERVIEW_PREP", jobRole + " | " + experienceLevel, response, 0);

        return AiResponse.builder()
                .result(response)
                .sessionType("INTERVIEW_PREP")
                .build();
    }

    public AiResponse generateCoverLetter(User user, String jobRole,
                                          String companyName, String resumeSummary) {
        String prompt = String.format("""
                Write a professional cover letter for the following:
                
                Job Role: %s
                Company: %s
                Candidate Summary: %s
                
                The cover letter should:
                - Be professional but personable
                - Highlight relevant experience
                - Show enthusiasm for the role
                - Be concise (3-4 paragraphs)
                - Follow Indian professional standards
                """, jobRole, companyName, resumeSummary);

        String response = callClaudeApi(prompt);
        saveSession(user, "COVER_LETTER", jobRole + " at " + companyName, response, 0);

        return AiResponse.builder()
                .result(response)
                .sessionType("COVER_LETTER")
                .build();
    }
    private String callClaudeApi(String prompt) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);  // ✅ OpenRouter uses Bearer token
            // Optional but recommended by OpenRouter:
            headers.set("HTTP-Referer", "http://localhost:8080");
            headers.set("X-Title", "JobPilot");

            Map<String, Object> body = Map.of(
                    "model", model,                             // e.g. "openai/gpt-4o-mini"
                    "max_tokens", 2000,
                    "messages", List.of(Map.of("role", "user", "content", prompt))
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                // ✅ OpenRouter returns OpenAI-style response: choices[0].message.content
                List<Map<String, Object>> choices =
                        (List<Map<String, Object>>) response.getBody().get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return (String) message.get("content");
                }
            }
            return "AI service is currently unavailable. Please try again later.";
        } catch (Exception e) {
            log.error("Error calling OpenRouter API: {}", e.getMessage());
            return "AI service error: " + e.getMessage();
        }
    }

    private void saveSession(User user, String type, String input,
                             String response, Integer tokens) {
        AiSession session = AiSession.builder()
                .user(user)
                .sessionType(type)
                .inputData(input)
                .aiResponse(response)
                .tokensUsed(tokens)
                .build();
        aiSessionRepository.save(session);
    }
}