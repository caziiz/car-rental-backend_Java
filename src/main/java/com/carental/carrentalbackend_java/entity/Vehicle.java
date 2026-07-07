package com.carental.carrentalbackend_java.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicleid")
    private Integer vehicleId;

    @NotBlank(message = "Make is required")
    @Column(name = "make")
    private String make;

    @NotBlank(message = "Model is required")
    @Column(name = "model")
    private String model;

    @NotNull(message = "Year is required")
    @Min(value = 1990, message = "Year must be 1990 or later")
    @Max(value = 2030, message = "Year must be 2030 or earlier")
    @Column(name = "year")
    private Integer year;

    @NotBlank(message = "License plate is required")
    @Column(name = "licenseplate")
    private String licensePlate;

    @NotBlank(message = "Category is required")
    @Column(name = "category")
    private String category;

    @NotNull(message = "Daily rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Daily rate must be greater than 0")
    @Column(name = "dailyrate")
    private BigDecimal dailyRate;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "Available|Rented|Maintenance", message = "Status must be Available, Rented or Maintenance")
    @Column(name = "status")
    private String status;

    @NotNull(message = "Mileage is required")
    @Min(value = 0, message = "Mileage cannot be negative")
    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "createdat")
    private LocalDateTime createdAt;
}