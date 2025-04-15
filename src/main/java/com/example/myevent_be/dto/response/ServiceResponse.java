package com.example.myevent_be.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceResponse {
    String id;
    String name;
    String description;
    String image;
    BigDecimal hourly_salary;
    int quantity;
    String place;
}
