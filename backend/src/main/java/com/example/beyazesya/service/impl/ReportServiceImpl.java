package com.example.beyazesya.service.impl;

import com.example.beyazesya.repository.CustomerRepository;
import com.example.beyazesya.repository.CustomerSaleProcedureResult;
import com.example.beyazesya.repository.ReportRepository;
import com.example.beyazesya.service.ReportService;
import com.example.beyazesya.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerSaleProcedureResult> getCustomerSales(Integer customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        return reportRepository.getCustomerSalesProcedure(customerId);
    }
}
