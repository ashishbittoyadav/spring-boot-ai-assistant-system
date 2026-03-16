package com.ashish.ai_assistant.repository;

import com.ashish.ai_assistant.model.QueryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository<QueryHistory,Long> {
}
