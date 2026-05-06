package com.example.beyazesya.service;

import com.example.beyazesya.repository.CustomerSaleProcedureResult;

import java.util.List;

public interface ReportService {

    List<CustomerSaleProcedureResult> getCustomerSales(Integer customerId);
}
