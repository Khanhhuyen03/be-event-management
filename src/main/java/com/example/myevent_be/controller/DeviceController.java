package com.example.myevent_be.controller;

import com.example.myevent_be.dto.request.DeviceRequest;
import com.example.myevent_be.dto.response.DeviceResponse;
import com.example.myevent_be.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    // Lấy danh sách thiết bị
    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    // Lấy chi tiết thiết bị
    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDeviceById(@PathVariable String id) {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    // Chỉ Admin mới được thêm thiết bị
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<DeviceResponse> createDevice(@RequestBody DeviceRequest request) {
        return ResponseEntity.ok(deviceService.createDevice(request));
    }

    // Chỉ Admin mới được cập nhật thiết bị
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponse> updateDevice(
            @PathVariable String id,
            @RequestBody DeviceRequest deviceRequest) {
        DeviceResponse updatedDevice = deviceService.updateDevice(id, deviceRequest);
        return ResponseEntity.ok(updatedDevice);
    }

    // Chỉ Admin mới được xóa thiết bị
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable String id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}
