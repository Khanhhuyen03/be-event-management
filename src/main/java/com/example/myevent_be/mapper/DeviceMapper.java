package com.example.myevent_be.mapper;

import com.example.myevent_be.dto.request.DeviceRequest;
import com.example.myevent_be.dto.request.DeviceTypeRequest;
import com.example.myevent_be.dto.response.DeviceResponse;
import com.example.myevent_be.entity.Device;
import com.example.myevent_be.entity.Device_Type;
import com.example.myevent_be.entity.EventType;
import com.example.myevent_be.exception.AppException;
import com.example.myevent_be.exception.ErrorCode;
import com.example.myevent_be.exception.ResourceNotFoundException;
import com.example.myevent_be.repository.DeviceTypeRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")

public interface DeviceMapper {

    //DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);
    //@Mapping(source = "deviceType_id", target = "device_type.id")
    default Device toDevice(DeviceRequest request,@Context DeviceTypeRepository deviceTypeRepository){
        if ( request == null ) {
            return null;
        }

        Device device = new Device();

        if (request.getDeviceType_id() != null) {
            Device_Type deviceType = deviceTypeRepository.findById(request.getDeviceType_id())
                    .orElseThrow(() -> new ResourceNotFoundException("DeviceType not found"));
            device.setDevice_type(deviceType);
        }
        device.setName( request.getName() );
        device.setDescription( request.getDescription() );
        device.setImage( request.getImage() );
        device.setHourly_rental_fee(request.getHourlyRentalFee());
        device.setQuantity( request.getQuantity() );
        device.setPlace(request.getPlace());
        return device;
    }



    //@Mapping(source = "id", target = "id") // Nếu field trong DTO khác với entity
    default DeviceResponse toResponse(Device device){
        if ( device == null ) {
            return null;
        }

        DeviceResponse.DeviceResponseBuilder deviceResponse = DeviceResponse.builder();

        deviceResponse.id( device.getId() );
        deviceResponse.name( device.getName() );
        deviceResponse.description( device.getDescription() );
        deviceResponse.image( device.getImage() );
        deviceResponse.quantity( device.getQuantity() );
        deviceResponse.hourlyRentalFee(device.getHourly_rental_fee());
        deviceResponse.place(device.getPlace());
        deviceResponse.deviceType_id(device.getDevice_type().getId());

        return deviceResponse.build();
    }
    void updateDevice(@MappingTarget Device device, DeviceRequest request);
}
