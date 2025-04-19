package com.example.myevent_be.service;

import com.example.myevent_be.dto.request.RentalRequest;
import com.example.myevent_be.dto.response.RentalResponse;
import com.example.myevent_be.entity.Event;
import com.example.myevent_be.entity.Rental;
import com.example.myevent_be.entity.User;
import com.example.myevent_be.repository.EventRepository;
import com.example.myevent_be.repository.RentalRepository;
import com.example.myevent_be.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public RentalResponse createRental(RentalRequest request) {
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
                
        Event event = eventRepository.findById(request.getEvent_id())
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setEvent(event);
        rental.setTotal_price(request.getTotal_price());
        rental.setRental_start_time(request.getRental_start_time());
        rental.setRental_end_time(request.getRental_end_time());
        rental.setCustom_location(request.getCustom_location());
        
        rental = rentalRepository.save(rental);
        return mapToResponse(rental);
    }

    public List<RentalResponse> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RentalResponse getRentalById(String id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));
        return mapToResponse(rental);
    }

    public RentalResponse updateRental(String id, RentalRequest request) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));

        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
                
        Event event = eventRepository.findById(request.getEvent_id())
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        rental.setUser(user);
        rental.setEvent(event);
        rental.setTotal_price(request.getTotal_price());
        rental.setRental_start_time(request.getRental_start_time());
        rental.setRental_end_time(request.getRental_end_time());
        rental.setCustom_location(request.getCustom_location());

        rental = rentalRepository.save(rental);
        return mapToResponse(rental);
    }

    public void deleteRental(String id) {
        if (!rentalRepository.existsById(id)) {
            throw new EntityNotFoundException("Rental not found with id: " + id);
        }
        rentalRepository.deleteById(id);
    }

    private RentalResponse mapToResponse(Rental rental) {
        return RentalResponse.builder()
                .id(rental.getId())
                .user_id(rental.getUser().getId())
                .event_id(rental.getEvent().getId())
                .total_price(rental.getTotal_price())
                .rental_start_time(rental.getRental_start_time())
                .rental_end_time(rental.getRental_end_time())
                .custom_location(rental.getCustom_location())
                .create_at(rental.getCreate_at())
                .update_at(rental.getUpdate_at())
                .build();
    }
} 