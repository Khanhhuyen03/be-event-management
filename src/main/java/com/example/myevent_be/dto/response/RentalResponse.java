package com.example.myevent_be.dto.response;

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
public class RentalResponse {
    private String id;
    private String user_id;
    private String event_id;
    private BigDecimal total_price;
    private Date rental_start_time;
    private Date rental_end_time;
    private String custom_location;
    private Date create_at;
    private Date update_at;
} 