package com.example.beyazesya.service.impl;

import com.example.beyazesya.entity.ProductStockView;
import com.example.beyazesya.repository.ProductStockViewRepository;
import com.example.beyazesya.repository.SaleReportView;
import com.example.beyazesya.repository.SaleReportViewRepository;
import com.example.beyazesya.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {

    private final ProductStockViewRepository productStockViewRepository;
    private final SaleReportViewRepository saleReportViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductStockView> getProductStockView() {
        return productStockViewRepository.findAllProductStock();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleReportView> getSaleReportView() {
        return saleReportViewRepository.findAllSaleReports();
    }
}
