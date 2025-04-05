package com.example.myevent_be.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DeviceRequest {
    private String name;
    private String description;
    private String image;
    private BigDecimal hourlyRentalFee;
    private int quantity;
    private String deviceType_id;
    private String place;
}