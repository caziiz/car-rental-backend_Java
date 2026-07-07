package com.carental.carrentalbackend_java.service;

import com.carental.carrentalbackend_java.dto.response.ApiResponse;
import com.carental.carrentalbackend_java.entity.Rental;
import com.carental.carrentalbackend_java.entity.Vehicle;
import com.carental.carrentalbackend_java.repository.CustomerRepository;
import com.carental.carrentalbackend_java.repository.RentalRepository;
import com.carental.carrentalbackend_java.repository.VehicleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
public class RentalService {

    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ApiResponse getAllRentals(int rentalId) {
        try {
            List<Rental> rentals;
            if (rentalId != 0) {
                String jpql = """
                    SELECT r FROM Rental r
                    WHERE r.rentalId = :rentalId
                    """;
                rentals = entityManager.createQuery(jpql, Rental.class)
                        .setParameter("rentalId", rentalId)
                        .getResultList();
            } else {
                String jpql = """
                    SELECT r FROM Rental r
                    ORDER BY r.rentalId DESC
                    """;
                rentals = entityManager.createQuery(jpql, Rental.class)
                        .getResultList();
            }

            // Populate customerName and vehicleName
            rentals.forEach(r -> {
                customerRepository.findById(r.getCustomerId())
                        .ifPresent(c -> r.setCustomerName(c.getFullName()));
                vehicleRepository.findById(r.getVehicleId())
                        .ifPresent(v -> r.setVehicleName(
                                v.getMake() + " " + v.getModel() + " (" + v.getYear() + ")"
                        ));
            });

            if (rentals.isEmpty())
                return new ApiResponse(false, "Rental not found");

            return new ApiResponse(true, "Rental data found", rentals);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    @Transactional
    public ApiResponse addRental(Rental r) {
        try {
            long totalDays = ChronoUnit.DAYS.between(r.getStartDate(), r.getExpectedEndDate());
            BigDecimal totalAmount = r.getDailyRate().multiply(BigDecimal.valueOf(totalDays));

            r.setTotalDays((int) totalDays);
            r.setTotalAmount(totalAmount);
            r.setStatus("Active");
            r.setCreatedAt(LocalDateTime.now());
            rentalRepository.save(r);

            // Update vehicle status to Rented
            vehicleRepository.findById(r.getVehicleId()).ifPresent(v -> {
                v.setStatus("Rented");
                vehicleRepository.save(v);
            });

            return new ApiResponse(true, "Rental created successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    @Transactional
    public ApiResponse returnVehicle(int rentalId, int vehicleId) {
        try {
            Optional<Rental> rentalOpt = rentalRepository.findById(rentalId);
            if (rentalOpt.isEmpty())
                return new ApiResponse(false, "Rental not found");

            Rental rental = rentalOpt.get();
            LocalDate today = LocalDate.now();
            long actualDays = ChronoUnit.DAYS.between(rental.getStartDate(), today);
            if (actualDays == 0) actualDays = 1;

            BigDecimal actualAmount = rental.getDailyRate().multiply(BigDecimal.valueOf(actualDays));

            rental.setStatus("Returned");
            rental.setActualEndDate(today);
            rental.setTotalDays((int) actualDays);
            rental.setTotalAmount(actualAmount);
            rentalRepository.save(rental);

            // Update vehicle status to Available
            vehicleRepository.findById(vehicleId).ifPresent(v -> {
                v.setStatus("Available");
                vehicleRepository.save(v);
            });

            return new ApiResponse(true, "Vehicle returned successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    @Transactional
    public ApiResponse deleteRental(int rentalId) {
        try {
            if (!rentalRepository.existsById(rentalId))
                return new ApiResponse(false, "Rental not found");

            // Delete related payments first
            entityManager.createQuery("DELETE FROM Payment p WHERE p.rentalId = :rentalId")
                    .setParameter("rentalId", rentalId)
                    .executeUpdate();

            rentalRepository.deleteById(rentalId);
            return new ApiResponse(true, "Rental deleted successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }
}