package com.example.beyazesya.service;

import com.example.beyazesya.dto.SaleRequest;
import com.example.beyazesya.dto.SaleResponse;

import java.util.List;

public interface SaleService {

    List<SaleResponse> getAllSales();

    SaleResponse getSaleById(Integer id);

    List<SaleResponse> getSalesByCustomerId(Integer customerId);

    SaleResponse createSale(SaleRequest request);

    SaleResponse updateSale(Integer id, SaleRequest request);

    void deleteSale(Integer id);

    void createBasicSale(Integer customerId, Integer employeeId);
}
