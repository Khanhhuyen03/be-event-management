package com.example.myevent_be.repository;

import com.example.myevent_be.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {
}
