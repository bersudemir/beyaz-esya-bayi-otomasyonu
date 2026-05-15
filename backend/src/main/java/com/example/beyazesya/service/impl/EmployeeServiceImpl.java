package com.example.beyazesya.service.impl;

import com.example.beyazesya.dto.EmployeeRequest;
import com.example.beyazesya.dto.EmployeeResponse;
import com.example.beyazesya.entity.Employee;
import com.example.beyazesya.exception.BusinessException;
import com.example.beyazesya.exception.ResourceNotFoundException;
import com.example.beyazesya.repository.EmployeeRepository;
import com.example.beyazesya.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Integer employeeId) {
        Employee employee = findEmployeeOrThrow(employeeId);
        return toResponse(employee);
    }

    @Override
    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        validateUniquePhone(request.phone(), null);

        Employee employee = new Employee();
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setPhone(request.phone());
        employee.setPosition(request.position());
        employee.setSalary(request.salary());

        return toResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(Integer employeeId, EmployeeRequest request) {
        Employee employee = findEmployeeOrThrow(employeeId);

        validateUniquePhone(request.phone(), employeeId);

        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setPhone(request.phone());
        employee.setPosition(request.position());
        employee.setSalary(request.salary());

        return toResponse(employeeRepository.save(employee));
    }

    private Employee findEmployeeOrThrow(Integer employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
    }

    private void validateUniquePhone(String phone, Integer currentEmployeeId) {
        employeeRepository.findByPhone(phone)
                .filter(employee -> !employee.getEmployeeId().equals(currentEmployeeId))
                .ifPresent(employee -> {
                    throw new BusinessException("Phone is already used by another employee");
                });
    }

    private EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPhone(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getCreatedAt()
        );
    }
}
