package com.example.beyazesya.service;

import com.example.beyazesya.dto.EmployeeRequest;
import com.example.beyazesya.dto.EmployeeResponse;
import java.util.List;

public interface EmployeeService {

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse getEmployeeById(Integer employeeId);

    EmployeeResponse createEmployee(EmployeeRequest request);

    EmployeeResponse updateEmployee(Integer employeeId, EmployeeRequest request);
}
