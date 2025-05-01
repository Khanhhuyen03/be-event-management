package com.example.myevent_be.repository;

import com.example.myevent_be.entity.TimeLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineRepository extends JpaRepository<TimeLine, String> {
}