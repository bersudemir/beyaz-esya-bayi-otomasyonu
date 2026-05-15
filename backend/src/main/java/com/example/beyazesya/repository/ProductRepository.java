package com.example.beyazesya.repository;

import com.example.beyazesya.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategory_CategoryId(Integer categoryId);

    @Query(value = """
            SELECT
                product_id AS productId,
                product_name AS productName,
                brand,
                category_name AS categoryName,
                price,
                stock_quantity AS stockQuantity
            FROM vw_ProductStock
            ORDER BY product_name
            """, nativeQuery = true)
    List<ProductStockProjection> findProductStockView();

    @Query(value = """
            SELECT
                product_id AS productId,
                product_name AS productName,
                brand,
                category_name AS categoryName,
                price,
                stock_quantity AS stockQuantity
            FROM vw_ProductStock
            WHERE product_id = :productId
            """, nativeQuery = true)
    ProductStockProjection findProductStockViewByProductId(@Param("productId") Integer productId);
}
