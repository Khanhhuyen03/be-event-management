package com.example.myevent_be.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Table(name = "contract")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    Rental rental;

    String name;
    UUID payment_intent_id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    Enum status;
    Date create_at;
    Date update_at;

    @OneToMany(mappedBy = "contract")
    Set<EmailSendLog> emailSendLogs;
}
