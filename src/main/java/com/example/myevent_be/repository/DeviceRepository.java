package com.example.myevent_be.repository;

import com.example.myevent_be.entity.Device;
import com.example.myevent_be.entity.Device_Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, String> {
    Optional<Device> findByNameAndDevice_type(String name, Device_Type deviceType);
}
