package com.ashish.ai_assistant.service;

import com.ashish.ai_assistant.model.QueryHistory;
import com.ashish.ai_assistant.repository.QueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AIService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final QueryRepository queryRepository;

    AIService(QueryRepository queryRepository){
        this.queryRepository = queryRepository;
    }

    public String askAI(String question){
        String url = "http://localhost:11434/api/generate";
        Map<String,Object> request = new HashMap<>();
        request.put("model","llama3");
        request.put("prompt",question);
        request.put("stream",false);

        Map apiResponse = restTemplate.postForObject(url,request,Map.class);

        String response = apiResponse.get("response").toString();

        QueryHistory queryHistory = new QueryHistory();
        queryHistory.setQuestion(question);
        queryHistory.setResponse(response);
        queryHistory.setCreatedAt(LocalDateTime.now());

        queryRepository.save(queryHistory);

        return response;
    }
}
