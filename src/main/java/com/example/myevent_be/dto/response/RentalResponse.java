package com.example.myevent_be.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentalResponse {
    String id;
    String userId;
    String eventId;
    BigDecimal totalPrice;
    Date rentalStartTime;
    Date rentalEndTime;
    String customLocation;
    Date createdAt;
    Date updatedAt;

} 