package com.example.beyazesya.repository;

import com.example.beyazesya.entity.ProductStockView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProductStockViewRepository extends Repository<ProductStockView, Integer> {

    @Query(value = "SELECT * FROM vw_ProductStock", nativeQuery = true)
    List<ProductStockView> findAllProductStock();
}
