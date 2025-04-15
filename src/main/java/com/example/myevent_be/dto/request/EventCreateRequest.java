package com.example.myevent_be.dto.request;

import com.example.myevent_be.entity.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventCreateRequest {

    @NotBlank(message = "Name is required")
    String name;
    
    String description;
    String detail;
    String img;
    
    @NotNull(message = "event_format must be specified")
    boolean event_format;
    
    @NotNull(message = "is_template must be specified")
    boolean is_template;
    
    String online_link;
    String invitation_link;
    
    @NotBlank(message = "eventType_id is required")
    String eventType_id;
}
