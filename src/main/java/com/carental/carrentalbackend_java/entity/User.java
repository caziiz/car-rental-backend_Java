package com.carental.carrentalbackend_java.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Integer userId;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    @Column(name = "fullname")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password must be at least 4 characters")
    @Column(name = "passwordhash")
    private String passwordHash;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "Admin|Staff", message = "Role must be Admin or Staff")
    @Column(name = "role")
    private String role;

    @Column(name = "isactive")
    private Boolean isActive;

    @Column(name = "createdat")
    private LocalDateTime createdAt;
}