package com.ashish.ai_assistant.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(Instant startsAt) {
        this.startsAt = startsAt;
    }

    public Instant getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Instant endsAt) {
        this.endsAt = endsAt;
    }

    private String alertName;
    private String severity;

    @Column(length = 1000)
    private String summary;

    private String status;

    private Instant startsAt;
    private Instant endsAt;

    public String getAiAnalysis() {
        return aiAnalysis;
    }

    public void setAiAnalysis(String aiAnalysis) {
        this.aiAnalysis = aiAnalysis;
    }

    @Lob
    @Column
    private String aiAnalysis;

    // Constructors
    public Alert() {}

    public Alert(String alertName, String severity, String summary,
                 String status, Instant startsAt, Instant endsAt,String aiAnalysis) {
        this.alertName = alertName;
        this.severity = severity;
        this.summary = summary;
        this.status = status;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.aiAnalysis = aiAnalysis;
    }

    // Getters & Setters
}