package com.carental.carrentalbackend_java.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerid")
    private Integer customerId;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    @Column(name = "fullname")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(min = 7, max = 20, message = "Phone must be between 7 and 20 characters")
    @Column(name = "phone")
    private String phone;

    @NotBlank(message = "National ID is required")
    @Column(name = "nationalid")
    private String nationalId;

    @Column(name = "address")
    private String address;

    @NotBlank(message = "License number is required")
    @Column(name = "licensenumber")
    private String licenseNumber;

    @Column(name = "isblacklisted")
    private Boolean isBlacklisted;

    @Column(name = "createdat")
    private LocalDateTime createdAt;
}