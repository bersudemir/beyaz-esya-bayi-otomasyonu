package com.example.beyazesya.service.impl;

import com.example.beyazesya.dto.EmployeeRequest;
import com.example.beyazesya.dto.EmployeeResponse;
import com.example.beyazesya.entity.Employee;
import com.example.beyazesya.exception.ResourceNotFoundException;
import com.example.beyazesya.mapper.EmployeeMapper;
import com.example.beyazesya.repository.EmployeeRepository;
import com.example.beyazesya.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Integer id) {
        return EmployeeMapper.toResponse(findEmployeeById(id));
    }

    @Override
    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Employee employee = EmployeeMapper.toEntity(request);
        return EmployeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(Integer id, EmployeeRequest request) {
        Employee employee = findEmployeeById(id);
        EmployeeMapper.updateEntity(employee, request);
        return EmployeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public void deleteEmployee(Integer id) {
        Employee employee = findEmployeeById(id);
        employeeRepository.delete(employee);
    }

    private Employee findEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }
}
