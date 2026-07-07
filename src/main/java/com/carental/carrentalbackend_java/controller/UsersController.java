package com.carental.carrentalbackend_java.controller;

import com.carental.carrentalbackend_java.dto.response.ApiResponse;
import com.carental.carrentalbackend_java.entity.User;
import com.carental.carrentalbackend_java.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers() {
        ApiResponse result = userService.getAllUsers(0);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable int userId) {
        ApiResponse result = userService.getAllUsers(userId);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody User u) {
        ApiResponse result = userService.addUser(u);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable int userId, @Valid @RequestBody User u) {
        ApiResponse result = userService.updateUser(userId, u);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int userId) {
        ApiResponse result = userService.deleteUser(userId);
        return result.getStatus() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }
}