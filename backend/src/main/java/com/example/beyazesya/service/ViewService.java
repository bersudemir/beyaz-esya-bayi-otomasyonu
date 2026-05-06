package com.example.beyazesya.service;

import com.example.beyazesya.entity.ProductStockView;
import com.example.beyazesya.repository.SaleReportView;

import java.util.List;

public interface ViewService {

    List<ProductStockView> getProductStockView();

    List<SaleReportView> getSaleReportView();
}
