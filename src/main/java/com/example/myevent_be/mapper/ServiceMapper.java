package com.example.myevent_be.mapper;


import com.example.myevent_be.dto.request.ServiceRequest;
import com.example.myevent_be.dto.response.ServiceResponse;
import com.example.myevent_be.entity.Service;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    Service toService (ServiceRequest request);
    ServiceResponse toServiceRespones (Service service);
    void updateService (@MappingTarget Service service, ServiceRequest request);
}
