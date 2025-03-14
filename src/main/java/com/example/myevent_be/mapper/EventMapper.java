package com.example.myevent_be.mapper;

import com.example.myevent_be.dto.request.EventCreateRequest;
import com.example.myevent_be.dto.request.EventUpdateRequest;
import com.example.myevent_be.dto.response.EventResponse;
import com.example.myevent_be.entity.Event;
import com.example.myevent_be.entity.EventType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(source = "eventType_id", target = "event_type.id")
    Event toEvent(EventCreateRequest request);

//    @Mapping(source = "event_type.id", target = "eventType_id") // 👈 Map khi trả về
    EventResponse toEventResponse(Event event);
    // Custom mapping method để lấy EventType từ eventTypeId
    @Named("mapEventType")
    default EventType mapEventType(String eventTypeId) {
        if (eventTypeId == null) return null;
        EventType eventType = new EventType();
        eventType.setId(eventTypeId); // Gán ID vào EventType
        return eventType;
    }

    void updateEvent(@MappingTarget Event event, EventUpdateRequest request);
}
