package com.example.myevent_be.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AIService {

    @Value("${google.ai.api.key}")
    String apiKey;  // API Key của bạn

    String url = "https://generativeai.googleapis.com/v1beta2/models/gemini-1.5-pro:generateText"; // Endpoint API của Google AI Studio

    public String generateText(String prompt) {
        // Tạo body request
        String requestBody = "{\n" +
                "  \"prompt\": \"" + prompt + "\",\n" +
                "  \"temperature\": 0.7,\n" +
                "  \"max_output_tokens\": 150\n" +
                "}";

        // Tạo headers cho request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Tạo request entity với body và headers
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Sử dụng RestTemplate để gửi request POST
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.getBody();  // Trả về text kết quả từ AI
    }
}
