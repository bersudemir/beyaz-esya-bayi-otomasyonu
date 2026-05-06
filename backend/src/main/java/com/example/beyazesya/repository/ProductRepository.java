package com.example.beyazesya.repository;

import com.example.beyazesya.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategory_CategoryId(Integer categoryId);

    @Modifying
    @Query(value = "EXEC sp_UpdateProductStock @product_id = :product_id, @new_stock = :new_stock", nativeQuery = true)
    void updateProductStockProcedure(
            @Param("product_id") Integer productId,
            @Param("new_stock") Integer newStock
    );
}
