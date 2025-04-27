package com.example.myevent_be.controller;

import com.example.myevent_be.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private AIService googleAIService;

    @PostMapping("/generate")
    public String generate(@RequestBody String prompt) {
        return googleAIService.generateText(prompt);
    }
}
