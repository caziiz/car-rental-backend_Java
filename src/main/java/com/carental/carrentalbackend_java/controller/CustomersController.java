package com.carental.carrentalbackend_java.controller;

import com.carental.carrentalbackend_java.dto.response.ApiResponse;
import com.carental.carrentalbackend_java.entity.Customer;
import com.carental.carrentalbackend_java.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Customers")
@RequiredArgsConstructor
public class CustomersController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCustomers() {
        ApiResponse result = customerService.getAllCustomers(0);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse> getCustomerById(@PathVariable int customerId) {
        ApiResponse result = customerService.getAllCustomers(customerId);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addCustomer(@Valid @RequestBody Customer c) {
        ApiResponse result = customerService.addCustomer(c);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateCustomer(@Valid @RequestBody Customer c) {
        ApiResponse result = customerService.updateCustomer(c);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable int customerId) {
        ApiResponse result = customerService.deleteCustomer(customerId);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }
}