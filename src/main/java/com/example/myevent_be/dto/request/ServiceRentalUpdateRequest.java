package com.example.myevent_be.dto.request;

import lombok.Data;

@Data
public class ServiceRentalUpdateRequest {

    private String serviceId;
    private Integer quantity;
    private String rentalId;
}