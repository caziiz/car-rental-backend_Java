package com.carental.carrentalbackend_java.controller;

import com.carental.carrentalbackend_java.dto.response.ApiResponse;
import com.carental.carrentalbackend_java.entity.Vehicle;
import com.carental.carrentalbackend_java.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Vehicles")
@RequiredArgsConstructor
public class VehiclesController {

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllVehicles() {
        ApiResponse result = vehicleService.getAllVehicles(0);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<ApiResponse> getVehicleById(@PathVariable int vehicleId) {
        ApiResponse result = vehicleService.getAllVehicles(vehicleId);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addVehicle(@Valid @RequestBody Vehicle v) {
        ApiResponse result = vehicleService.addVehicle(v);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateVehicle(@Valid @RequestBody Vehicle v) {
        ApiResponse result = vehicleService.updateVehicle(v);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<ApiResponse> deleteVehicle(@PathVariable int vehicleId) {
        ApiResponse result = vehicleService.deleteVehicle(vehicleId);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }
}