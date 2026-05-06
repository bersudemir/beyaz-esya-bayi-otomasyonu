package com.example.beyazesya.service.impl;

import com.example.beyazesya.dto.CustomerRequest;
import com.example.beyazesya.dto.CustomerResponse;
import com.example.beyazesya.entity.Customer;
import com.example.beyazesya.exception.ResourceNotFoundException;
import com.example.beyazesya.mapper.CustomerMapper;
import com.example.beyazesya.repository.CustomerRepository;
import com.example.beyazesya.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Integer id) {
        return CustomerMapper.toResponse(findCustomerById(id));
    }

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = CustomerMapper.toEntity(request);
        return CustomerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(Integer id, CustomerRequest request) {
        Customer customer = findCustomerById(id);
        CustomerMapper.updateEntity(customer, request);
        return CustomerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    @Transactional
    public void deleteCustomer(Integer id) {
        Customer customer = findCustomerById(id);
        customerRepository.delete(customer);
    }

    private Customer findCustomerById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }
}
