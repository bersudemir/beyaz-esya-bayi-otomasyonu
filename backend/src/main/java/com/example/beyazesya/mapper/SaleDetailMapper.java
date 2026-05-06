package com.example.beyazesya.mapper;

import com.example.beyazesya.dto.SaleDetailRequest;
import com.example.beyazesya.dto.SaleDetailResponse;
import com.example.beyazesya.entity.Product;
import com.example.beyazesya.entity.Sale;
import com.example.beyazesya.entity.SaleDetail;

public final class SaleDetailMapper {

    private SaleDetailMapper() {
    }

    public static SaleDetail toEntity(SaleDetailRequest request, Sale sale, Product product) {
        return SaleDetail.builder()
                .sale(sale)
                .product(product)
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .build();
    }

    public static void updateEntity(SaleDetail saleDetail, SaleDetailRequest request, Sale sale, Product product) {
        saleDetail.setSale(sale);
        saleDetail.setProduct(product);
        saleDetail.setQuantity(request.getQuantity());
        saleDetail.setUnitPrice(request.getUnitPrice());
    }

    public static SaleDetailResponse toResponse(SaleDetail saleDetail) {
        Sale sale = saleDetail.getSale();
        Product product = saleDetail.getProduct();

        return SaleDetailResponse.builder()
                .saleDetailId(saleDetail.getSaleDetailId())
                .saleId(sale != null ? sale.getSaleId() : null)
                .productId(product != null ? product.getProductId() : null)
                .productName(product != null ? product.getProductName() : null)
                .quantity(saleDetail.getQuantity())
                .unitPrice(saleDetail.getUnitPrice())
                .build();
    }
}
