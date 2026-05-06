package com.example.beyazesya.mapper;

import com.example.beyazesya.dto.EmployeeRequest;
import com.example.beyazesya.dto.EmployeeResponse;
import com.example.beyazesya.entity.Employee;

import java.time.LocalDateTime;

public final class EmployeeMapper {

    private EmployeeMapper() {
    }

    public static Employee toEntity(EmployeeRequest request) {
        return Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .position(request.getPosition())
                .salary(request.getSalary())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static void updateEntity(Employee employee, EmployeeRequest request) {
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setPhone(request.getPhone());
        employee.setPosition(request.getPosition());
        employee.setSalary(request.getSalary());
    }

    public static EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .phone(employee.getPhone())
                .position(employee.getPosition())
                .salary(employee.getSalary())
                .createdAt(employee.getCreatedAt())
                .build();
    }
}
