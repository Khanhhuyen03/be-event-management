package com.example.myevent_be.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DeviceResponse {
    private String id;
    private String name;
    private String description;
    private String image;
    private BigDecimal hourlyRentalFee;
    private int quantity;
}
