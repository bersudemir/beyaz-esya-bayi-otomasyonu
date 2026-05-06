package com.example.beyazesya.service.impl;

import com.example.beyazesya.dto.SaleDetailRequest;
import com.example.beyazesya.dto.SaleDetailResponse;
import com.example.beyazesya.entity.Product;
import com.example.beyazesya.entity.Sale;
import com.example.beyazesya.entity.SaleDetail;
import com.example.beyazesya.exception.ResourceNotFoundException;
import com.example.beyazesya.mapper.SaleDetailMapper;
import com.example.beyazesya.repository.ProductRepository;
import com.example.beyazesya.repository.SaleDetailRepository;
import com.example.beyazesya.repository.SaleRepository;
import com.example.beyazesya.service.SaleDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleDetailServiceImpl implements SaleDetailService {

    private final SaleDetailRepository saleDetailRepository;
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SaleDetailResponse> getAllSaleDetails() {
        return saleDetailRepository.findAll()
                .stream()
                .map(SaleDetailMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SaleDetailResponse getSaleDetailById(Integer id) {
        return SaleDetailMapper.toResponse(findSaleDetailById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleDetailResponse> getSaleDetailsBySaleId(Integer saleId) {
        return saleDetailRepository.findBySale_SaleId(saleId)
                .stream()
                .map(SaleDetailMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public SaleDetailResponse createSaleDetail(SaleDetailRequest request) {
        Sale sale = findSaleById(request.getSaleId());
        Product product = findProductById(request.getProductId());
        SaleDetail saleDetail = SaleDetailMapper.toEntity(request, sale, product);
        return SaleDetailMapper.toResponse(saleDetailRepository.save(saleDetail));
    }

    @Override
    @Transactional
    public SaleDetailResponse updateSaleDetail(Integer id, SaleDetailRequest request) {
        SaleDetail saleDetail = findSaleDetailById(id);
        Sale sale = findSaleById(request.getSaleId());
        Product product = findProductById(request.getProductId());
        SaleDetailMapper.updateEntity(saleDetail, request, sale, product);
        return SaleDetailMapper.toResponse(saleDetailRepository.save(saleDetail));
    }

    @Override
    @Transactional
    public void deleteSaleDetail(Integer id) {
        SaleDetail saleDetail = findSaleDetailById(id);
        saleDetailRepository.delete(saleDetail);
    }

    private SaleDetail findSaleDetailById(Integer id) {
        return saleDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale detail not found with id: " + id));
    }

    private Sale findSaleById(Integer id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));
    }

    private Product findProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
}
