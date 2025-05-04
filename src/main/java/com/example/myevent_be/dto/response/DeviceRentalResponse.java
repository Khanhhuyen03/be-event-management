package com.example.myevent_be.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;

@Data
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceRentalResponse {
    String id;
    String deviceId;
    String deviceName;
    String deviceDescription;
    String deviceTypeName;
    BigDecimal hourlyRentalFee;
    String rentalId;
    Date rentalStartTime;
    Date rentalEndTime;
    Integer quantity;
    Date createAt;
    Date updateAt;
}