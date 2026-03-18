package com.ashish.ai_assistant.dto;

public record AlertResponse(
        String alertName,
        String severity,
        String summary,
        String status,
        String aiAnalysis
) {
}
