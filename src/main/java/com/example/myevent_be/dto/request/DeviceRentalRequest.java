package com.example.myevent_be.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeviceRentalRequest {
    @NotBlank(message = "Device ID is required")
    private String deviceId;

    @NotBlank(message = "Device type is required")
    private String deviceType;

    @NotBlank(message = "Device name is required")
    private String deviceName;

    @NotBlank(message = "Supplier is required")
    private String supplier;

    @NotNull(message = "Price per day is required")
    @Positive(message = "Price must be positive")
    private BigDecimal pricePerDay;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    private String rentalId;
}