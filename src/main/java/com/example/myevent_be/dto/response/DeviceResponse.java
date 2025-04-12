package com.example.myevent_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceResponse {
    private String id;
    private String name;
    private String description;
    private String image;
    private BigDecimal hourlyRentalFee;
    private int quantity;
    private String place;
    private String deviceType_id;
}
