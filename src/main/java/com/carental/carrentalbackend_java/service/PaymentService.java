package com.carental.carrentalbackend_java.service;

import com.carental.carrentalbackend_java.dto.response.ApiResponse;
import com.carental.carrentalbackend_java.entity.Payment;
import com.carental.carrentalbackend_java.repository.CustomerRepository;
import com.carental.carrentalbackend_java.repository.PaymentRepository;
import com.carental.carrentalbackend_java.repository.RentalRepository;
import com.carental.carrentalbackend_java.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    // Get all payments — returns a List directly
    public ApiResponse getAllPayments() {
        try {
            List<Payment> payments = paymentRepository.findAllOrderedDesc();

            payments.forEach(p -> {
                rentalRepository.findById(p.getRentalId()).ifPresent(r -> {
                    customerRepository.findById(r.getCustomerId())
                            .ifPresent(c -> p.setCustomerName(c.getFullName()));
                    vehicleRepository.findById(r.getVehicleId())
                            .ifPresent(v -> p.setVehicleName(
                                    v.getMake() + " " + v.getModel() + " (" + v.getYear() + ")"
                            ));
                });
            });

            return new ApiResponse(true, "Payment data found", payments);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    // Get single payment by ID
    public ApiResponse getPaymentById(int paymentId) {
        try {
            Optional<Payment> payment = paymentRepository.findById(paymentId);
            if (payment.isEmpty())
                return new ApiResponse(false, "Payment not found");

            Payment p = payment.get();
            rentalRepository.findById(p.getRentalId()).ifPresent(r -> {
                customerRepository.findById(r.getCustomerId())
                        .ifPresent(c -> p.setCustomerName(c.getFullName()));
                vehicleRepository.findById(r.getVehicleId())
                        .ifPresent(v -> p.setVehicleName(
                                v.getMake() + " " + v.getModel() + " (" + v.getYear() + ")"
                        ));
            });

            return new ApiResponse(true, "Payment data found", p);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    @Transactional
    public ApiResponse addPayment(Payment p) {
        try {
            p.setPaymentDate(LocalDateTime.now());
            paymentRepository.save(p);

            if ("Completed".equals(p.getStatus())) {
                rentalRepository.findById(p.getRentalId()).ifPresent(rental -> {
                    LocalDate today = LocalDate.now();
                    long days = ChronoUnit.DAYS.between(rental.getStartDate(), today);
                    if (days == 0) days = 1;

                    rental.setStatus("Returned");
                    rental.setActualEndDate(today);
                    rental.setTotalDays((int) days);
                    rental.setTotalAmount(p.getAmount());
                    rentalRepository.save(rental);

                    vehicleRepository.findById(rental.getVehicleId()).ifPresent(v -> {
                        v.setStatus("Available");
                        vehicleRepository.save(v);
                    });
                });
            }

            return new ApiResponse(true, "Payment added successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    public ApiResponse updatePaymentStatus(int paymentId, String status) {
        try {
            Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
            if (paymentOpt.isEmpty())
                return new ApiResponse(false, "Payment not found");

            Payment payment = paymentOpt.get();
            payment.setStatus(status);
            paymentRepository.save(payment);
            return new ApiResponse(true, "Payment status updated successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    public ApiResponse deletePayment(int paymentId) {
        try {
            if (!paymentRepository.existsById(paymentId))
                return new ApiResponse(false, "Payment not found");
            paymentRepository.deleteById(paymentId);
            return new ApiResponse(true, "Payment deleted successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }
}