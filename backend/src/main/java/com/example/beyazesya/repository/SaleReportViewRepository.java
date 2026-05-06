package com.example.beyazesya.repository;

import com.example.beyazesya.entity.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface SaleReportViewRepository extends Repository<Sale, Integer> {

    @Query(value = "SELECT * FROM vw_SaleReport", nativeQuery = true)
    List<SaleReportView> findAllSaleReports();
}
