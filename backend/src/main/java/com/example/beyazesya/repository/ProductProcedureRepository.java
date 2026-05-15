package com.example.beyazesya.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductProcedureRepository {

    private final JdbcTemplate jdbcTemplate;

    public void updateProductStock(Integer productId, Integer newStock) {
        jdbcTemplate.update(
                "EXEC sp_UpdateProductStock @product_id = ?, @new_stock = ?",
                productId,
                newStock
        );
    }
}
