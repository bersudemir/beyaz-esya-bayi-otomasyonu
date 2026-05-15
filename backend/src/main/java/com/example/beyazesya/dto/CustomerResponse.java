package com.example.beyazesya.dto;

import java.time.LocalDateTime;

public record CustomerResponse(
        Integer customerId,
        String firstName,
        String lastName,
        String phone,
        String email,
        LocalDateTime createdAt
) {
}
