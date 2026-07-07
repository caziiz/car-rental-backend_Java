package com.carental.carrentalbackend_java.service;

import com.carental.carrentalbackend_java.dto.response.ApiResponse;
import com.carental.carrentalbackend_java.entity.Vehicle;
import com.carental.carrentalbackend_java.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public ApiResponse getAllVehicles(int vehicleId) {
        try {
            if (vehicleId != 0) {
                Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
                return vehicle.map(v -> new ApiResponse(true, "Vehicle data found", List.of(v)))
                        .orElse(new ApiResponse(false, "Vehicle not found"));
            }
            List<Vehicle> vehicles = vehicleRepository.findAll()
                    .stream()
                    .sorted((a, b) -> b.getVehicleId() - a.getVehicleId())
                    .toList();
            return new ApiResponse(true, "Vehicle data found", vehicles);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    public ApiResponse addVehicle(Vehicle v) {
        try {
            v.setCreatedAt(LocalDateTime.now());
            vehicleRepository.save(v);
            return new ApiResponse(true, "Vehicle added successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    public ApiResponse updateVehicle(Vehicle v) {
        try {
            Optional<Vehicle> existing = vehicleRepository.findById(v.getVehicleId());
            if (existing.isEmpty())
                return new ApiResponse(false, "Vehicle not found");

            Vehicle vehicle = existing.get();
            vehicle.setMake(v.getMake());
            vehicle.setModel(v.getModel());
            vehicle.setYear(v.getYear());
            vehicle.setLicensePlate(v.getLicensePlate());
            vehicle.setCategory(v.getCategory());
            vehicle.setDailyRate(v.getDailyRate());
            vehicle.setStatus(v.getStatus());
            vehicle.setMileage(v.getMileage());
            vehicleRepository.save(vehicle);
            return new ApiResponse(true, "Vehicle updated successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    public ApiResponse deleteVehicle(int vehicleId) {
        try {
            if (!vehicleRepository.existsById(vehicleId))
                return new ApiResponse(false, "Vehicle not found");
            vehicleRepository.deleteById(vehicleId);
            return new ApiResponse(true, "Vehicle deleted successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }
}