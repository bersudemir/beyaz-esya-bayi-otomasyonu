package com.example.beyazesya.repository;

import com.example.beyazesya.entity.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends Repository<Sale, Integer> {

    @Query(value = "EXEC sp_GetCustomerSales @customer_id = :customer_id", nativeQuery = true)
    List<CustomerSaleProcedureResult> getCustomerSalesProcedure(@Param("customer_id") Integer customerId);
}
