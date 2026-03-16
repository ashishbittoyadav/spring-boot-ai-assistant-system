package com.ashish.ai_assistant.service;

import com.ashish.ai_assistant.model.QueryHistory;
import com.ashish.ai_assistant.repository.QueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final QueryRepository queryRepository;

    AIService(QueryRepository queryRepository){
        this.queryRepository = queryRepository;
    }

    public String askAI(String sessionId,String question){
        List<QueryHistory> history = queryRepository.findBySessionIdOrderByCreatedAtAsc(sessionId);

        StringBuilder context = new StringBuilder();

        for (QueryHistory h: history){
            context.append("User:").append(h.getQuestion()).append("\n");
            context.append("AI:").append(h.getResponse()).append("\n");
        }

        context.append("User:").append(question).append("\n");

//        String url = "http://localhost:11434/api/generate";
        String url = "http://ollama:11434/api/generate";
        Map<String,Object> request = new HashMap<>();
        request.put("model","llama3");
        request.put("prompt",context.toString());
        request.put("stream",false);

        Map apiResponse = restTemplate.postForObject(url,request,Map.class);

        String response = apiResponse.get("response").toString();

        QueryHistory queryHistory = new QueryHistory();
        queryHistory.setQuestion(question);
        queryHistory.setResponse(response);
        queryHistory.setSessionId(sessionId);
        queryHistory.setCreatedAt(LocalDateTime.now());

        queryRepository.save(queryHistory);

        return response;
    }
}
