package com.ashish.ai_assistant.controller;

import com.ashish.ai_assistant.dto.AlertResponse;
import com.ashish.ai_assistant.model.Alert;
import com.ashish.ai_assistant.repository.AlertRepository;
import com.ashish.ai_assistant.service.AIService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AIService aiService;
    private final AlertRepository alertRepository;

    AlertController(AIService aiService, AlertRepository alertRepository){
        this.aiService = aiService;
        this.alertRepository = alertRepository;
    }

    @PostMapping
    public String handleAlert(@RequestBody Map<String, Object> payload) {

        System.out.println("==== ALERT RECEIVED ====");

        List<Map<String, Object>> alerts =
                (List<Map<String, Object>>) payload.get("alerts");

        for (Map<String, Object> alert : alerts) {

            Map<String, String> labels =
                    (Map<String, String>) alert.get("labels");

            Map<String, String> annotations =
                    (Map<String, String>) alert.get("annotations");

            String alertName = labels.get("alertname");
            String severity = labels.get("severity");
            String summary = annotations.get("summary");

            String startsAtStr = (String) alert.get("startsAt");
            String endsAtStr = (String) alert.get("endsAt");
            String status = (String) alert.get("status");

            System.out.println("Alert: " + alertName);
            System.out.println("Severity: " + severity);
            System.out.println("Summary: " + summary);

            String aiResponse = aiService.analyzeAlert(payload.toString());

            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(aiResponse);
                // Extract the "response" field from the JSON
                if (root.has("response")) {
                    aiResponse = root.get("response").asText();
                }
            } catch (Exception e) {
                e.printStackTrace();
                aiResponse = "AI response parsing failed";
            }

            Alert alertEntity = new Alert(
                    alertName,
                    severity,
                    summary,
                    status,
                    Instant.parse(startsAtStr),
                    endsAtStr.equals("0001-01-01T00:00:00Z") ? null : Instant.parse(endsAtStr),
                    aiResponse
            );
            alertRepository.save(alertEntity);
        }
        return "Alerts processed successfully";
    }

    @GetMapping
    public List<AlertResponse> getAllAlerts(){
        return alertRepository.findAll(Sort.by(Sort.Direction.DESC, "startsAt"))
                .stream()
                .map(a->new AlertResponse(
                        a.getAlertName(),
                        a.getSeverity(),
                        a.getSummary(),
                        a.getStatus(),
                        a.getAiAnalysis()
                )).toList();
    }

    @GetMapping("/severity/{level}")
    public List<AlertResponse> getBySeverity(@PathVariable String level) {
        return alertRepository.findBySeverity(level)
                .stream()
                .map(a->new AlertResponse(
                        a.getAlertName(),
                        a.getSeverity(),
                        a.getSummary(),
                        a.getStatus(),
                        a.getAiAnalysis()
                )).toList();
    }
    @GetMapping("/latest")
    public List<AlertResponse> getLatestAlerts(){
        return alertRepository
                .findAll(Sort.by(Sort.Direction.DESC,"startsAt"))
                .stream()
                .map(a->new AlertResponse(
                        a.getAlertName(),
                        a.getSeverity(),
                        a.getSummary(),
                        a.getStatus(),
                        a.getAiAnalysis()
                ))
                .limit(10)
                .toList();
    }

}
