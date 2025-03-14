package com.example.myevent_be.dto.response;

import com.example.myevent_be.entity.EventType;
import com.example.myevent_be.entity.Rental;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Data
@Table(name = "event")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventResponse {

    String id;
    String name;
    String description;
    String detail;
    String img;
    boolean event_format;
    boolean is_template;
    String online_link;
    String invitation_link;
    Date created_at;
    Date update_at;
    String eventType_id;

    @OneToMany(mappedBy = "event")
    Set<Rental> rentals;
}
