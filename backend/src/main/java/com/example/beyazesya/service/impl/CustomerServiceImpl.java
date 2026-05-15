package com.example.beyazesya.service.impl;

import com.example.beyazesya.dto.CustomerRequest;
import com.example.beyazesya.dto.CustomerResponse;
import com.example.beyazesya.entity.Customer;
import com.example.beyazesya.exception.BusinessException;
import com.example.beyazesya.exception.ResourceNotFoundException;
import com.example.beyazesya.repository.CustomerRepository;
import com.example.beyazesya.service.CustomerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Integer customerId) {
        Customer customer = findCustomerOrThrow(customerId);
        return toResponse(customer);
    }

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        validateUniquePhone(request.phone(), null);
        validateUniqueEmail(request.email(), null);

        Customer customer = new Customer();
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setPhone(request.phone());
        customer.setEmail(request.email());

        return toResponse(customerRepository.save(customer));
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(Integer customerId, CustomerRequest request) {
        Customer customer = findCustomerOrThrow(customerId);

        validateUniquePhone(request.phone(), customerId);
        validateUniqueEmail(request.email(), customerId);

        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setPhone(request.phone());
        customer.setEmail(request.email());

        return toResponse(customerRepository.save(customer));
    }

    private Customer findCustomerOrThrow(Integer customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
    }

    private void validateUniquePhone(String phone, Integer currentCustomerId) {
        customerRepository.findByPhone(phone)
                .filter(customer -> !customer.getCustomerId().equals(currentCustomerId))
                .ifPresent(customer -> {
                    throw new BusinessException("Phone is already used by another customer");
                });
    }

    private void validateUniqueEmail(String email, Integer currentCustomerId) {
        customerRepository.findByEmail(email)
                .filter(customer -> !customer.getCustomerId().equals(currentCustomerId))
                .ifPresent(customer -> {
                    throw new BusinessException("Email is already used by another customer");
                });
    }

    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getCreatedAt()
        );
    }
}
