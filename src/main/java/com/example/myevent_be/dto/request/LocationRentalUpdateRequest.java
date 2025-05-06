package com.example.myevent_be.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationRentalUpdateRequest {

    private String locationName;
    private String address;
    private BigDecimal pricePerDay;
    private Integer quantity;
    private String rentalId;
} 