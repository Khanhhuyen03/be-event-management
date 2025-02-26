package com.example.myevent_be.mapper;

import com.example.myevent_be.dto.request.DeviceRequest;
import com.example.myevent_be.dto.response.DeviceResponse;
import com.example.myevent_be.entity.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    Device toEntity(DeviceRequest request);

    @Mapping(source = "id", target = "id") // Nếu field trong DTO khác với entity
    DeviceResponse toResponse(Device device);
}
