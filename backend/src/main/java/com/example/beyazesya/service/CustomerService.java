package com.example.beyazesya.service;

import com.example.beyazesya.dto.CustomerRequest;
import com.example.beyazesya.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(Integer id);

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse updateCustomer(Integer id, CustomerRequest request);

    void deleteCustomer(Integer id);
}
