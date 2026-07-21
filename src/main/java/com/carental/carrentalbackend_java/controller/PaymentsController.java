package com.carental.carrentalbackend_java.controller;

import com.carental.carrentalbackend_java.dto.response.ApiResponse;
import com.carental.carrentalbackend_java.entity.Payment;
import com.carental.carrentalbackend_java.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Payments")
@RequiredArgsConstructor
public class PaymentsController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllPayments() {
        ApiResponse result = paymentService.getAllPayments(0);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> getPaymentById(@PathVariable int paymentId) {
        ApiResponse result = paymentService.getAllPayments(paymentId);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addPayment(@Valid @RequestBody Payment p) {
        ApiResponse result = paymentService.addPayment(p);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/status/{paymentId}/{status}")
    public ResponseEntity<ApiResponse> updatePaymentStatus(@PathVariable int paymentId, @PathVariable String status) {
        ApiResponse result = paymentService.updatePaymentStatus(paymentId, status);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> deletePayment(@PathVariable int paymentId) {
        ApiResponse result = paymentService.deletePayment(paymentId);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }
}