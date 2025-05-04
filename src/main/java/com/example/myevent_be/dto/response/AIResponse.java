package com.example.myevent_be.dto.response;

import com.example.myevent_be.entity.Event;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AIResponse {
    String message;
    List<String> eventTypes;
    List<EventResponse> events;
}
