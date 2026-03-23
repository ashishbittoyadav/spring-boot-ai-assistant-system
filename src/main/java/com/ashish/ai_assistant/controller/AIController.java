package com.ashish.ai_assistant.controller;

import com.ashish.ai_assistant.model.QueryHistory;
import com.ashish.ai_assistant.repository.QueryRepository;
import com.ashish.ai_assistant.service.AIService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai")
public class AIController {
    private final AIService aiService;
    private final QueryRepository queryRepository;

    AIController(AIService aiService,QueryRepository queryRepository){
        this.aiService = aiService;
        this.queryRepository = queryRepository;
    }

    @PostMapping("/chat")
    public Map<String, String> askAI(@RequestBody Map<String,String> request){

        String question = request.get("question");
        String sessionId = request.get("sessionId");
        return aiService.askAI(sessionId,question);
    }



    @GetMapping("/history")
    public List<QueryHistory> getHistory(){
        return queryRepository.findAll();
    }
}
