package com.carental.carrentalbackend_java.repository;

import com.carental.carrentalbackend_java.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByStatus(String status);

    @Query("SELECT p FROM Payment p ORDER BY p.paymentId DESC")
    List<Payment> findAllOrderedDesc();
}