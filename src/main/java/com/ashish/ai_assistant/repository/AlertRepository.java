package com.ashish.ai_assistant.repository;

import com.ashish.ai_assistant.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert,Long> {
    List<Alert> findBySeverity(String severity);
}
