package com.example.myevent_be.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import jakarta.validation.constraints.NotNull;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentalRequest {

    String userId;
    String eventId;
    BigDecimal totalPrice;
    Date rentalStartTime;
    Date rentalEndTime;
    String customLocation;
} 