package com.example.beyazesya.mapper;

import com.example.beyazesya.dto.SaleRequest;
import com.example.beyazesya.dto.SaleResponse;
import com.example.beyazesya.entity.Customer;
import com.example.beyazesya.entity.Employee;
import com.example.beyazesya.entity.Sale;

public final class SaleMapper {

    private SaleMapper() {
    }

    public static Sale toEntity(SaleRequest request, Customer customer, Employee employee) {
        return Sale.builder()
                .customer(customer)
                .employee(employee)
                .saleDate(request.getSaleDate())
                .totalAmount(request.getTotalAmount())
                .saleStatus(request.getSaleStatus())
                .build();
    }

    public static void updateEntity(Sale sale, SaleRequest request, Customer customer, Employee employee) {
        sale.setCustomer(customer);
        sale.setEmployee(employee);
        sale.setSaleDate(request.getSaleDate());
        sale.setTotalAmount(request.getTotalAmount());
        sale.setSaleStatus(request.getSaleStatus());
    }

    public static SaleResponse toResponse(Sale sale) {
        Customer customer = sale.getCustomer();
        Employee employee = sale.getEmployee();

        return SaleResponse.builder()
                .saleId(sale.getSaleId())
                .customerId(customer != null ? customer.getCustomerId() : null)
                .customerName(customer != null ? fullName(customer.getFirstName(), customer.getLastName()) : null)
                .employeeId(employee != null ? employee.getEmployeeId() : null)
                .employeeName(employee != null ? fullName(employee.getFirstName(), employee.getLastName()) : null)
                .saleDate(sale.getSaleDate())
                .totalAmount(sale.getTotalAmount())
                .saleStatus(sale.getSaleStatus())
                .build();
    }

    private static String fullName(String firstName, String lastName) {
        return String.join(" ", firstName, lastName).trim();
    }
}
