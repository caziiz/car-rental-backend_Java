package com.carental.carrentalbackend_java.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentid")
    private Integer paymentId;

    @NotNull(message = "Rental ID is required")
    @Column(name = "rentalid")
    private Integer rentalId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "paymentdate")
    private LocalDateTime paymentDate;

    @NotBlank(message = "Payment method is required")
    @Pattern(regexp = "Cash|Credit Card|Debit Card|Bank Transfer",
            message = "Payment method must be Cash, Credit Card, Debit Card or Bank Transfer")
    @Column(name = "paymentmethod")
    private String paymentMethod;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "Completed|Pending|Refunded",
            message = "Status must be Completed, Pending or Refunded")
    @Column(name = "status")
    private String status;

    @Column(name = "notes")
    private String notes;

    @Transient
    private String customerName;

    @Transient
    private String vehicleName;
}