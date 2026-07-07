package com.carental.carrentalbackend_java.service;

import com.carental.carrentalbackend_java.dto.response.ApiResponse;
import com.carental.carrentalbackend_java.entity.User;
import com.carental.carrentalbackend_java.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ApiResponse getAllUsers(int userId) {
        try {
            if (userId != 0) {
                Optional<User> user = userRepository.findById(userId);
                return user.map(u -> new ApiResponse(true, "Users found", List.of(u)))
                        .orElse(new ApiResponse(false, "User not found"));
            }
            List<User> users = userRepository.findAll()
                    .stream()
                    .sorted((a, b) -> b.getUserId() - a.getUserId())
                    .toList();
            return new ApiResponse(true, "Users found", users);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    public ApiResponse addUser(User u) {
        try {
            // Store password as plain text to match frontend behavior
            u.setIsActive(true);
            u.setCreatedAt(LocalDateTime.now());
            userRepository.save(u);
            return new ApiResponse(true, "User added successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    public ApiResponse updateUser(int userId, User u) {
        try {
            Optional<User> existing = userRepository.findById(userId);
            if (existing.isEmpty())
                return new ApiResponse(false, "User not found");

            User user = existing.get();
            user.setFullName(u.getFullName());
            user.setEmail(u.getEmail());
            user.setRole(u.getRole());
            user.setIsActive(u.getIsActive());
            userRepository.save(user);
            return new ApiResponse(true, "User updated successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    public ApiResponse deleteUser(int userId) {
        try {
            if (!userRepository.existsById(userId))
                return new ApiResponse(false, "User not found");
            userRepository.deleteById(userId);
            return new ApiResponse(true, "User deleted successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }
}