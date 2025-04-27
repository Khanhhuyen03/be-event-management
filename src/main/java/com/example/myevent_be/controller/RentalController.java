package com.example.myevent_be.controller;

import com.example.myevent_be.dto.request.RentalRequest;

import com.example.myevent_be.dto.response.RentalResponse;
import com.example.myevent_be.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import com.example.myevent_be.dto.response.ApiResponse;
import com.example.myevent_be.dto.response.RentalResponse;
import com.example.myevent_be.exception.AppException;
import com.example.myevent_be.exception.ErrorCode;
import com.example.myevent_be.service.RentalService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @GetMapping
    public List<RentalResponse> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable String id) {
        return rentalService.getRentalById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public RentalResponse createRental(@RequestBody RentalRequest request) {
        return rentalService.createRental(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalResponse> updateRental(@PathVariable String id, @RequestBody RentalRequest request) {
        return rentalService.updateRental(id, request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable String id) {
        if (!rentalService.deleteRental(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

