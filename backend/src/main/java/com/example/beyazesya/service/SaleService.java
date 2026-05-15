package com.example.beyazesya.service;

import com.example.beyazesya.dto.AddSaleDetailRequest;
import com.example.beyazesya.dto.CreateSaleRequest;
import com.example.beyazesya.dto.SaleReportResponse;
import com.example.beyazesya.dto.SaleResponse;
import com.example.beyazesya.dto.UpdateSaleDetailQuantityRequest;
import com.example.beyazesya.dto.UpdateSaleStatusRequest;
import java.util.List;

public interface SaleService {

    SaleResponse createSale(CreateSaleRequest request);

    SaleResponse addSaleDetail(Integer saleId, AddSaleDetailRequest request);

    SaleResponse updateSaleDetailQuantity(
            Integer saleId,
            Integer productId,
            UpdateSaleDetailQuantityRequest request
    );

    SaleResponse updateSaleStatus(Integer saleId, UpdateSaleStatusRequest request);

    List<SaleReportResponse> getCustomerSales(Integer customerId);

    List<SaleReportResponse> getSaleReport();
}
