package com.example.myevent_be.mapper;

import com.example.myevent_be.dto.response.DeviceRentalResponse;
import com.example.myevent_be.entity.DeviceRental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeviceRentalMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "device.device_type.name", target = "device_type_name")
    @Mapping(source = "device.name", target = "device_name")
   // @Mapping(source = "device.user.name", target = "supplier_name")
    @Mapping(source = "device.hourly_rental_fee", target = "hourly_rental_fee")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "rental.total_price", target = "total_price")
    @Mapping(source = "create_at", target = "create_at")
    @Mapping(source = "update_at", target = "update_at")
    DeviceRentalResponse toDeviceRentalResponse(DeviceRental deviceRental);
}