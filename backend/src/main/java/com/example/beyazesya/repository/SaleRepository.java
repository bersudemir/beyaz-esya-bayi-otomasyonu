package com.example.beyazesya.repository;

import com.example.beyazesya.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Integer> {

    List<Sale> findByCustomer_CustomerId(Integer customerId);

    @Modifying
    @Query(value = "EXEC sp_CreateSale @customer_id = :customer_id, @employee_id = :employee_id", nativeQuery = true)
    void createSaleProcedure(
            @Param("customer_id") Integer customerId,
            @Param("employee_id") Integer employeeId
    );
}
