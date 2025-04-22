package com.example.myevent_be.mapper;

import com.example.myevent_be.dto.request.DeviceRequest;
import com.example.myevent_be.dto.response.DeviceResponse;
import com.example.myevent_be.entity.Device;
import com.example.myevent_be.entity.Device_Type;
import com.example.myevent_be.entity.User;
import com.example.myevent_be.exception.ResourceNotFoundException;
import com.example.myevent_be.repository.DeviceTypeRepository;
import com.example.myevent_be.repository.UserRepository;
import org.mapstruct.*;


@Mapper(componentModel = "spring")

public interface DeviceMapper {

    //DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);
    //@Mapping(source = "deviceType_id", target = "device_type.id")
    default Device toDevice(DeviceRequest request, @Context DeviceTypeRepository deviceTypeRepository, UserRepository userRepository){
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
        if (request.getUserID() != null) {
            User user = userRepository.findById(request.getUserID())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            device.setUser(user);
        }
        return device;
    }



    //@Mapping(source = "id", target = "id") // Nếu field trong DTO khác với entity
    default DeviceResponse toResponse(Device device){
        if ( device == null ) {
            return null;
        }

        return DeviceResponse.builder()
                .id(device.getId())
                .name(device.getName())
                .description(device.getDescription())
                .image(device.getImage())
                .quantity(device.getQuantity())
                .hourlyRentalFee(device.getHourly_rental_fee())
                .place(device.getPlace())
                .deviceType_id(device.getDevice_type().getId())
                .userID((device.getUser().getId()))
                .update_at(device.getUpdate_at())
                .created_at(device.getCreated_at())
                .build();
    }
    void updateDevice(@MappingTarget Device device, DeviceRequest request);
}
