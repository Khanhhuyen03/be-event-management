package com.example.myevent_be.service;

import com.example.myevent_be.dto.request.DeviceRentalRequest;
import com.example.myevent_be.dto.response.DeviceRentalResponse;
import com.example.myevent_be.entity.Device;
import com.example.myevent_be.entity.DeviceRental;
import com.example.myevent_be.entity.Device_Type;
import com.example.myevent_be.entity.Rental;
import com.example.myevent_be.exception.ResourceNotFoundException;
import com.example.myevent_be.mapper.DeviceRentalMapper;
import com.example.myevent_be.repository.DeviceRentalRepository;
import com.example.myevent_be.repository.DeviceRepository;
import com.example.myevent_be.repository.DeviceTypeRepository;
import com.example.myevent_be.repository.RentalRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DeviceRentalService {

    DeviceRentalRepository deviceRentalRepository;
    DeviceRentalMapper deviceRentalMapper;
    DeviceRepository deviceRepository;
    DeviceTypeRepository deviceTypeRepository;
    RentalRepository rentalRepository;

    public List<DeviceRentalResponse> getAllDeviceRentals() {
        log.info("Getting all device rentals");
        List<DeviceRental> deviceRentals = deviceRentalRepository.findAll();
        return deviceRentals.stream()
                .map(deviceRentalMapper::toDeviceRentalResponse)
                .toList();
    }

    public DeviceRentalResponse getDeviceRentalById(String id) {
        log.info("Getting device rental by id: {}", id);
        DeviceRental deviceRental = deviceRentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device rental not found with id: " + id));
        return deviceRentalMapper.toDeviceRentalResponse(deviceRental);
    }

    @Transactional
    public DeviceRentalResponse createDeviceRental(DeviceRentalRequest request) {
        log.info("Creating new device rental");

        // Lấy hoặc tạo mới Device_Type
        Device_Type deviceType = deviceTypeRepository.findByName(request.getDeviceType())
                .orElseGet(() -> {
                    Device_Type newType = new Device_Type();
                    newType.setName(request.getDeviceType());
                    return deviceTypeRepository.save(newType);
                });

        // Lấy hoặc tạo mới Device
        Device device = deviceRepository.findByNameAndDevice_type(request.getDeviceName(), deviceType)
                .orElseGet(() -> {
                    Device newDevice = new Device();
                    newDevice.setName(request.getDeviceName());
                    newDevice.setDevice_type(deviceType);
                    newDevice.setHourly_rental_fee(request.getPricePerDay());
                    newDevice.setQuantity(request.getQuantity());
                    return deviceRepository.save(newDevice);
                });

        // Tạo mới DeviceRental
        DeviceRental deviceRental = new DeviceRental();
        deviceRental.setDevice(device);
        deviceRental.setQuantity(request.getQuantity());

        // Nếu có rentalId, liên kết với Rental
        if (request.getRentalId() != null) {
            Rental rental = rentalRepository.findById(request.getRentalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: " + request.getRentalId()));
            deviceRental.setRental(rental);
        }

        // Lưu DeviceRental
        DeviceRental saved = deviceRentalRepository.save(deviceRental);
        return deviceRentalMapper.toDeviceRentalResponse(saved);
    }

    @Transactional
    public void deleteDeviceRental(String id) {
        log.info("Deleting device rental with id: {}", id);
        if (!deviceRentalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Device rental not found with id: " + id);
        }
        deviceRentalRepository.deleteById(id);
    }
}