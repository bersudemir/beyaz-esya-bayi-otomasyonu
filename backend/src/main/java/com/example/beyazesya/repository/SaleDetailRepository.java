package com.example.beyazesya.repository;

import com.example.beyazesya.entity.SaleDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Integer> {

    List<SaleDetail> findBySale_SaleId(Integer saleId);

    Optional<SaleDetail> findBySale_SaleIdAndProduct_ProductId(Integer saleId, Integer productId);

    boolean existsBySale_SaleIdAndProduct_ProductId(Integer saleId, Integer productId);
}
