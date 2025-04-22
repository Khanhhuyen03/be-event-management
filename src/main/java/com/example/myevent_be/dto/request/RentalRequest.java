package com.example.myevent_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequest {
    private String user_id;
    private String event_id;
    private BigDecimal total_price;
    private Date rental_start_time;
    private Date rental_end_time;
    private String custom_location;
} 