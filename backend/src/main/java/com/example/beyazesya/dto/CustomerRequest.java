package com.example.beyazesya.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerRequest(
        @NotBlank(message = "First name is required")
        @Size(max = 50, message = "First name can be at most 50 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 50, message = "Last name can be at most 50 characters")
        String lastName,

        @NotBlank(message = "Phone is required")
        @Size(max = 20, message = "Phone can be at most 20 characters")
        String phone,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 100, message = "Email can be at most 100 characters")
        String email
) {
}
