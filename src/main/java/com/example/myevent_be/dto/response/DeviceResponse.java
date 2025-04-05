package com.example.myevent_be.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceResponse {
    private String id;
    private String name;
    private String description;
    private String image;
    private BigDecimal hourlyRentalFee;
    private int quantity;
    private String deviceType_id;
}
