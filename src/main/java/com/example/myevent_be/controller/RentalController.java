package com.example.myevent_be.controller;

import com.example.myevent_be.dto.request.RentalRequest;
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
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RentalController {

    RentalService rentalService;

    @PostMapping
    public ApiResponse<RentalResponse> createRental(@RequestBody @Valid RentalRequest request) {
        RentalResponse response = rentalService.createRental(request);
        return ApiResponse.<RentalResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<RentalResponse>> getRentals() {
        List<RentalResponse> rentals = rentalService.getAllRentals();
        return ApiResponse.<List<RentalResponse>>builder()
                .result(rentals)
                .build();
    }

    @GetMapping("/{rentalId}")
    public ApiResponse<RentalResponse> getRentalById(@PathVariable String rentalId) {
        RentalResponse rental = rentalService.getRentalById(rentalId);
        return ApiResponse.<RentalResponse>builder()
                .result(rental)
                .build();
    }

    @PutMapping("/{rentalId}")
    public ApiResponse<RentalResponse> updateRental(
            @PathVariable String rentalId,
            @RequestBody @Valid RentalRequest request) {
        RentalResponse response = rentalService.updateRental(rentalId, request);
        return ApiResponse.<RentalResponse>builder()
                .result(response)
                .build();
    }

    @DeleteMapping("/{rentalId}")
    public ApiResponse<Void> deleteRental(@PathVariable String rentalId) {
        rentalService.deleteRental(rentalId);
        return ApiResponse.<Void>builder()
                .result(null)
                .build();
    }
}