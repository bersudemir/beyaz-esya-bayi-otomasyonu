package com.example.beyazesya.repository;

import com.example.beyazesya.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Integer> {

    List<SaleDetail> findBySale_SaleId(Integer saleId);
}
