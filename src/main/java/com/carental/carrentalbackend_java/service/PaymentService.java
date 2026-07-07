package com.carental.carrentalbackend_java.service;

import com.carental.carrentalbackend_java.dto.response.ApiResponse;
import com.carental.carrentalbackend_java.entity.Payment;
import com.carental.carrentalbackend_java.entity.Rental;
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

    public ApiResponse getAllPayments(int paymentId) {
        try {
            List<Payment> payments;
            if (paymentId != 0) {
                Optional<Payment> payment = paymentRepository.findById(paymentId);
                if (payment.isEmpty())
                    return new ApiResponse(false, "Payment not found");
                payments = List.of(payment.get());
            } else {
                payments = paymentRepository.findAll()
                        .stream()
                        .sorted((a, b) -> b.getPaymentId() - a.getPaymentId())
                        .toList();
            }

            // Populate customerName and vehicleName
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

            if (payments.isEmpty())
                return new ApiResponse(false, "Payment not found");

            return new ApiResponse(true, "Payment data found", payments);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    @Transactional
    public ApiResponse addPayment(Payment p) {
        try {
            p.setPaymentDate(LocalDateTime.now());
            paymentRepository.save(p);

            // If Completed, auto-return the rental and free the vehicle
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

    public ApiResponse getTotalRevenue() {
        try {
            BigDecimal total = paymentRepository.findByStatus("Completed")
                    .stream()
                    .map(Payment::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return new ApiResponse(true, "Total revenue calculated", total);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }
}