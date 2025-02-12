package com.example.myevent_be.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@Table(name = "event_type")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    Date created_at;
    Date update_at;

    @OneToMany(mappedBy = "event_type")
    Set<Event> event;
}
