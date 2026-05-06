package com.example.beyazesya.service.impl;

import com.example.beyazesya.dto.SaleRequest;
import com.example.beyazesya.dto.SaleResponse;
import com.example.beyazesya.entity.Customer;
import com.example.beyazesya.entity.Employee;
import com.example.beyazesya.entity.Sale;
import com.example.beyazesya.exception.ResourceNotFoundException;
import com.example.beyazesya.mapper.SaleMapper;
import com.example.beyazesya.repository.CustomerRepository;
import com.example.beyazesya.repository.EmployeeRepository;
import com.example.beyazesya.repository.SaleRepository;
import com.example.beyazesya.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponse> getAllSales() {
        return saleRepository.findAll()
                .stream()
                .map(SaleMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponse getSaleById(Integer id) {
        return SaleMapper.toResponse(findSaleById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponse> getSalesByCustomerId(Integer customerId) {
        return saleRepository.findByCustomer_CustomerId(customerId)
                .stream()
                .map(SaleMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public SaleResponse createSale(SaleRequest request) {
        Customer customer = findCustomerById(request.getCustomerId());
        Employee employee = findEmployeeById(request.getEmployeeId());
        Sale sale = SaleMapper.toEntity(request, customer, employee);
        return SaleMapper.toResponse(saleRepository.save(sale));
    }

    @Override
    @Transactional
    public SaleResponse updateSale(Integer id, SaleRequest request) {
        Sale sale = findSaleById(id);
        Customer customer = findCustomerById(request.getCustomerId());
        Employee employee = findEmployeeById(request.getEmployeeId());
        SaleMapper.updateEntity(sale, request, customer, employee);
        return SaleMapper.toResponse(saleRepository.save(sale));
    }

    @Override
    @Transactional
    public void deleteSale(Integer id) {
        Sale sale = findSaleById(id);
        saleRepository.delete(sale);
    }

    @Override
    @Transactional
    public void createBasicSale(Integer customerId, Integer employeeId) {
        findCustomerById(customerId);
        findEmployeeById(employeeId);
        saleRepository.createSaleProcedure(customerId, employeeId);
    }

    private Sale findSaleById(Integer id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));
    }

    private Customer findCustomerById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    private Employee findEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }
}
