package com.example.myevent_be.controller;

import com.example.myevent_be.dto.request.EventCreateRequest;
import com.example.myevent_be.dto.request.EventTypeUpdateRequest;
import com.example.myevent_be.dto.request.EventUpdateRequest;
import com.example.myevent_be.dto.response.ApiResponse;
import com.example.myevent_be.dto.response.EventResponse;
import com.example.myevent_be.dto.response.EventTypeResponse;
import com.example.myevent_be.service.EventService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventController {
    EventService eventService;

    @PostMapping("/create-event")
    ApiResponse<EventResponse> createEvent(@RequestBody @Valid EventCreateRequest request){
        return ApiResponse.<EventResponse>builder()
                .result(eventService.createEvent(request))
                .build();
    }

    @GetMapping
    List<EventResponse> getEvents(){
        return eventService.getEvents();
    } // xem danh sach su kien

    @GetMapping("/{eventId}")
    EventResponse getEvent(@PathVariable("eventId") String eventId){ // xem chi tiet su kien
        return eventService.getEvent(eventId);
    }

    @DeleteMapping("/{eventId}")
    String deleteEvent(@PathVariable String eventId){
        eventService.deleteEvent(eventId);
        return "Event has been deleted";
    }

    @PutMapping("/{eventId}")
    EventResponse updateEvent(@PathVariable String eventId, @RequestBody EventUpdateRequest request){
        return eventService.updateEvent(request, eventId);
    }
}
