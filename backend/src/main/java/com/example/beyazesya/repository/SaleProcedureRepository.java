package com.example.beyazesya.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SaleProcedureRepository {

    private final JdbcTemplate jdbcTemplate;

    public Integer createSale(Integer customerId, Integer employeeId) {
        return jdbcTemplate.queryForObject(
                "EXEC sp_CreateSale @customer_id = ?, @employee_id = ?",
                Integer.class,
                customerId,
                employeeId
        );
    }

    public void addSaleDetail(Integer saleId, Integer productId, Integer quantity) {
        jdbcTemplate.update(
                "EXEC sp_AddSaleDetail @sale_id = ?, @product_id = ?, @quantity = ?",
                saleId,
                productId,
                quantity
        );
    }

    public void updateSaleDetailQuantity(Integer saleId, Integer productId, Integer newQuantity) {
        jdbcTemplate.update(
                "EXEC sp_UpdateSaleDetailQuantity @sale_id = ?, @product_id = ?, @new_quantity = ?",
                saleId,
                productId,
                newQuantity
        );
    }

    public void updateSaleStatus(Integer saleId, String newStatus) {
        jdbcTemplate.update(
                "EXEC sp_UpdateSaleStatus @sale_id = ?, @new_status = ?",
                saleId,
                newStatus
        );
    }

    public List<SaleReportRow> findCustomerSales(Integer customerId) {
        return jdbcTemplate.query(
                "EXEC sp_GetCustomerSales @customer_id = ?",
                new SaleReportRowMapper(),
                customerId
        );
    }

    private static class SaleReportRowMapper implements RowMapper<SaleReportRow> {

        @Override
        public SaleReportRow mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new SaleReportRow(
                    rs.getInt("sale_id"),
                    rs.getTimestamp("sale_date").toLocalDateTime(),
                    rs.getString("sale_status"),
                    rs.getBigDecimal("total_amount"),
                    rs.getInt("customer_id"),
                    rs.getString("customer_name"),
                    rs.getInt("employee_id"),
                    rs.getString("employee_name"),
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("brand"),
                    rs.getInt("quantity"),
                    rs.getBigDecimal("unit_price"),
                    rs.getBigDecimal("line_total")
            );
        }
    }
}
