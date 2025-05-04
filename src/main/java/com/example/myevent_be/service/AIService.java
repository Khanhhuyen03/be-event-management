package com.example.myevent_be.service;

import com.example.myevent_be.dto.response.AIResponse;
import com.example.myevent_be.dto.response.EventResponse;
import com.example.myevent_be.entity.Event;
import com.example.myevent_be.entity.EventType;
import com.example.myevent_be.repository.EventRepository;
import com.example.myevent_be.repository.EventTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AIService {

    //    @Value("${google.ai.api.key}")
    String apiKey;  // API Key của bạn

    ObjectMapper objectMapper;
    RestTemplate restTemplate;
    EventTypeRepository eventTypeRepository;
    EventRepository eventRepository;

    String url = "https://generativeai.googleapis.com/v1beta2/models/gemini-1.5-pro:generateText"; // Endpoint API của Google AI Studio

    @Autowired
    public AIService(ObjectMapper objectMapper, RestTemplate restTemplate,
                     EventTypeRepository eventTypeRepository, EventRepository eventRepository,
                     @Value("${google.ai.api.key}") String apiKey) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.eventTypeRepository = eventTypeRepository;
        this.eventRepository = eventRepository;
        this.apiKey = apiKey;
    }
        public AIResponse startConversation() {
        AIResponse response = new AIResponse();
        response.setMessage("Bạn muốn tổ chức sự kiện gì? Đây là danh sách các loại sự kiện mà chúng tôi cung cấp ");

        // Lấy danh sách loại sự kiện từ database
        List<String> eventTypes = eventTypeRepository.findAll().stream()
                .map(EventType::getName)
                .collect(Collectors.toList());
        response.setEventTypes(eventTypes);

        return response;
    }


    public AIResponse generateResponse(String userInput) {
        AIResponse response = new AIResponse();

        // Kiểm tra loại sự kiện có tồn tại trong database
        if (!eventTypeRepository.findByName(userInput).isPresent()) {
            List<String> eventTypes = eventTypeRepository.findAll().stream()
                    .map(EventType::getName)
                    .collect(Collectors.toList());
            response.setMessage("Loại sự kiện không hợp lệ. Vui lòng chọn: " + String.join(", ", eventTypes));
            response.setEventTypes(eventTypes);
            return response;
        }

        // Gọi Gemini API để sinh câu trả lời tự nhiên
        String aiMessage = callGeminiAPI(userInput);
        response.setMessage(aiMessage);

        // Lấy danh sách sự kiện từ database
        List<Event> events = eventRepository.findByEventType_Name(userInput);
        List<EventResponse> eventDtos = events.stream().map(event -> {
            EventResponse dto = new EventResponse();
            dto.setId(event.getId());
            dto.setDetail(event.getDetail());
            dto.setImg(event.getImg());
            dto.setEventTypeName(event.getEventType().getName());
            dto.setName(event.getName());
            dto.setDescription(event.getDescription());
            return dto;
        }).collect(Collectors.toList());
        response.setEvents(eventDtos);
//        return response.setEvents("");
        return response;
    }


    private String callGeminiAPI(String prompt) {
        try {
            String url = "https://generativeai.googleapis.com/v1beta/models/gemini-1.5-pro:generateContent";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            String requestBody = objectMapper.writeValueAsString(
                    new GeminiRequest("Hãy trả lời tự nhiên: Bạn muốn tổ chức " + prompt + " như thế nào?"));

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return objectMapper.readTree(response.getBody()).get("text").asText();
            } else {
                log.error("Gemini API error: {}", response.getStatusCode());
                return "Có lỗi khi gọi AI. Vui lòng thử lại.";
            }
        } catch (Exception e) {
            log.error("Error calling Gemini API", e);
            return "Có lỗi khi xử lý yêu cầu.";
        }
    }

    private static class GeminiRequest {
        String prompt;

        GeminiRequest(String prompt) {
            this.prompt = prompt;
        }
    }
}
