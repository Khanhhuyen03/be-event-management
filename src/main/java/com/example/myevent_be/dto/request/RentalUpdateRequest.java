package com.example.myevent_be.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentalUpdateRequest {
    String userId;
    String eventId;
    BigDecimal totalPrice;
    Date rentalStartTime;
    Date rentalEndTime;
    String customLocation;
} 