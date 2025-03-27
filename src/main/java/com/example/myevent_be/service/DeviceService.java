package com.example.myevent_be.service;


import com.example.myevent_be.dto.request.DeviceRequest;
import com.example.myevent_be.dto.response.DeviceResponse;
import com.example.myevent_be.entity.Device;
import com.example.myevent_be.exception.ResourceNotFoundException;
import com.example.myevent_be.mapper.DeviceMapper;
import com.example.myevent_be.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    public DeviceService(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    public DeviceResponse createDevice(DeviceRequest requestDTO) {
        Device device = deviceMapper.toEntity(requestDTO);
        device = deviceRepository.save(device);
        return deviceMapper.toResponse(device);
    }

    public List<DeviceResponse> getAllDevices() {
        return deviceRepository.findAll().stream()
                .map(deviceMapper::toResponse)
                .collect(Collectors.toList());
    }

    public DeviceResponse getDeviceById(String id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id));
        return deviceMapper.toResponse(device);
    }

    public DeviceResponse updateDevice(String id, DeviceRequest request) {
        // Tìm thiết bị theo ID, nếu không có thì ném lỗi
        Device existingDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id));

        // Cập nhật thông tin từ request vào entity
        existingDevice.setName(request.getName());
        existingDevice.setDescription(request.getDescription()); // Thêm nếu cần
        existingDevice.setImage(request.getImage()); // Thêm nếu cần
//        existingDevice.setHourly_rental_fee(request.getHourlyRentalFee());
        existingDevice.setQuantity(request.getQuantity());
//        existingDevice.setUpdate_at(new Date());

        // Lưu lại vào database
        Device updatedDevice = deviceRepository.save(existingDevice);

        // Trả về response DTO
        return deviceMapper.toResponse(updatedDevice);
    }


    public void deleteDevice(String id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id));
        deviceRepository.delete(device);
    }

}

