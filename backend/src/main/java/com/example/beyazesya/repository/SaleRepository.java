package com.example.beyazesya.repository;

import com.example.beyazesya.entity.Sale;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepository extends JpaRepository<Sale, Integer> {

    List<Sale> findByCustomer_CustomerId(Integer customerId);

    @Query(value = """
            SELECT
                sale_id AS saleId,
                sale_date AS saleDate,
                sale_status AS saleStatus,
                total_amount AS totalAmount,
                customer_id AS customerId,
                customer_name AS customerName,
                employee_id AS employeeId,
                employee_name AS employeeName,
                product_id AS productId,
                product_name AS productName,
                brand,
                quantity,
                unit_price AS unitPrice,
                line_total AS lineTotal
            FROM vw_SaleReport
            ORDER BY sale_date DESC
            """, nativeQuery = true)
    List<SaleReportProjection> findSaleReportView();
}
