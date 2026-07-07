package com.carental.carrentalbackend_java.service;

import com.carental.carrentalbackend_java.dto.response.ApiResponse;
import com.carental.carrentalbackend_java.entity.Customer;
import com.carental.carrentalbackend_java.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public ApiResponse getAllCustomers(int customerId) {
        try {
            if (customerId != 0) {
                Optional<Customer> customer = customerRepository.findById(customerId);
                return customer.map(c -> new ApiResponse(true, "Customer data found", List.of(c)))
                        .orElse(new ApiResponse(false, "Customer not found"));
            }
            List<Customer> customers = customerRepository.findAll()
                    .stream()
                    .sorted((a, b) -> b.getCustomerId() - a.getCustomerId())
                    .toList();
            return new ApiResponse(true, "Customer data found", customers);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    public ApiResponse addCustomer(Customer c) {
        try {
            c.setIsBlacklisted(false);
            c.setCreatedAt(LocalDateTime.now());
            customerRepository.save(c);
            return new ApiResponse(true, "Customer added successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    public ApiResponse updateCustomer(Customer c) {
        try {
            Optional<Customer> existing = customerRepository.findById(c.getCustomerId());
            if (existing.isEmpty())
                return new ApiResponse(false, "Customer not found");

            Customer customer = existing.get();
            customer.setFullName(c.getFullName());
            customer.setEmail(c.getEmail());
            customer.setPhone(c.getPhone());
            customer.setNationalId(c.getNationalId());
            customer.setAddress(c.getAddress());
            customer.setLicenseNumber(c.getLicenseNumber());
            customer.setIsBlacklisted(c.getIsBlacklisted());
            customerRepository.save(customer);
            return new ApiResponse(true, "Customer updated successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    public ApiResponse deleteCustomer(int customerId) {
        try {
            if (!customerRepository.existsById(customerId))
                return new ApiResponse(false, "Customer not found");
            customerRepository.deleteById(customerId);
            return new ApiResponse(true, "Customer deleted successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }
}