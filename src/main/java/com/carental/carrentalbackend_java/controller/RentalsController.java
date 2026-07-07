package com.carental.carrentalbackend_java.controller;

import com.carental.carrentalbackend_java.dto.response.ApiResponse;
import com.carental.carrentalbackend_java.entity.Rental;
import com.carental.carrentalbackend_java.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Rentals")
@RequiredArgsConstructor
public class RentalsController {

    private final RentalService rentalService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllRentals() {
        ApiResponse result = rentalService.getAllRentals(0);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<ApiResponse> getRentalById(@PathVariable int rentalId) {
        ApiResponse result = rentalService.getAllRentals(rentalId);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addRental(@Valid @RequestBody Rental r) {
        ApiResponse result = rentalService.addRental(r);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/return/{rentalId}/{vehicleId}")
    public ResponseEntity<ApiResponse> returnVehicle(@PathVariable int rentalId, @PathVariable int vehicleId) {
        ApiResponse result = rentalService.returnVehicle(rentalId, vehicleId);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/{rentalId}")
    public ResponseEntity<ApiResponse> deleteRental(@PathVariable int rentalId) {
        ApiResponse result = rentalService.deleteRental(rentalId);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }
}