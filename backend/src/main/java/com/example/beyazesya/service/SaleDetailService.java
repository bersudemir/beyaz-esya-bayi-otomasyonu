package com.example.beyazesya.service;

import com.example.beyazesya.dto.SaleDetailRequest;
import com.example.beyazesya.dto.SaleDetailResponse;

import java.util.List;

public interface SaleDetailService {

    List<SaleDetailResponse> getAllSaleDetails();

    SaleDetailResponse getSaleDetailById(Integer id);

    List<SaleDetailResponse> getSaleDetailsBySaleId(Integer saleId);

    SaleDetailResponse createSaleDetail(SaleDetailRequest request);

    SaleDetailResponse updateSaleDetail(Integer id, SaleDetailRequest request);

    void deleteSaleDetail(Integer id);
}
