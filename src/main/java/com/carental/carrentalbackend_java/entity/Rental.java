package com.carental.carrentalbackend_java.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rentalid")
    private Integer rentalId;

    @NotNull(message = "Customer ID is required")
    @Column(name = "customerid")
    private Integer customerId;

    @NotNull(message = "Vehicle ID is required")
    @Column(name = "vehicleid")
    private Integer vehicleId;

    @NotNull(message = "User ID is required")
    @Column(name = "userid")
    private Integer userId;

    @NotNull(message = "Start date is required")
    @Column(name = "startdate")
    private LocalDate startDate;

    @NotNull(message = "Expected end date is required")
    @Column(name = "expectedenddate")
    private LocalDate expectedEndDate;

    @Column(name = "actualenddate")
    private LocalDate actualEndDate;

    @NotNull(message = "Daily rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Daily rate must be greater than 0")
    @Column(name = "dailyrate")
    private BigDecimal dailyRate;

    @Column(name = "totaldays")
    private Integer totalDays;

    @Column(name = "totalamount")
    private BigDecimal totalAmount;

    @Column(name = "status")
    private String status = "Active";

    @Column(name = "notes")
    private String notes;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Transient
    private String customerName;

    @Transient
    private String vehicleName;
}