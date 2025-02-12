package com.example.myevent_be.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@Table(name = "human_resources_rental")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class HumanResourcesRental {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "human_resources_id")
    HumanResources human_resources;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    Rental rental;

    int quantity;
    Date create_at;
    Date update_at;
}
