package com.carental.carrentalbackend_java.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private Boolean status;
    private String message;
    private Object data;

    // Constructor without data (for simple responses)
    public ApiResponse(Boolean status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }
}