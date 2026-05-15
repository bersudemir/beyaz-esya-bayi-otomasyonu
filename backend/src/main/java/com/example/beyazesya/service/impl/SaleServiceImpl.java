package com.example.beyazesya.service.impl;

import com.example.beyazesya.dto.AddSaleDetailRequest;
import com.example.beyazesya.dto.CreateSaleRequest;
import com.example.beyazesya.dto.SaleReportResponse;
import com.example.beyazesya.dto.SaleResponse;
import com.example.beyazesya.dto.UpdateSaleDetailQuantityRequest;
import com.example.beyazesya.dto.UpdateSaleStatusRequest;
import com.example.beyazesya.entity.Customer;
import com.example.beyazesya.entity.Employee;
import com.example.beyazesya.entity.Sale;
import com.example.beyazesya.exception.ResourceNotFoundException;
import com.example.beyazesya.repository.CustomerRepository;
import com.example.beyazesya.repository.SaleProcedureRepository;
import com.example.beyazesya.repository.SaleReportProjection;
import com.example.beyazesya.repository.SaleReportRow;
import com.example.beyazesya.repository.SaleRepository;
import com.example.beyazesya.service.SaleService;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final SaleProcedureRepository saleProcedureRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public SaleResponse createSale(CreateSaleRequest request) {
        Integer saleId = saleProcedureRepository.createSale(request.customerId(), request.employeeId());
        Sale sale = findFreshSaleOrThrow(saleId);
        return toSaleResponse(sale);
    }

    @Override
    @Transactional
    public SaleResponse addSaleDetail(Integer saleId, AddSaleDetailRequest request) {
        saleProcedureRepository.addSaleDetail(saleId, request.productId(), request.quantity());
        Sale sale = findFreshSaleOrThrow(saleId);
        return toSaleResponse(sale);
    }

    @Override
    @Transactional
    public SaleResponse updateSaleDetailQuantity(
            Integer saleId,
            Integer productId,
            UpdateSaleDetailQuantityRequest request
    ) {
        saleProcedureRepository.updateSaleDetailQuantity(saleId, productId, request.newQuantity());
        Sale sale = findFreshSaleOrThrow(saleId);
        return toSaleResponse(sale);
    }

    @Override
    @Transactional
    public SaleResponse updateSaleStatus(Integer saleId, UpdateSaleStatusRequest request) {
        saleProcedureRepository.updateSaleStatus(saleId, request.saleStatus());
        Sale sale = findFreshSaleOrThrow(saleId);
        return toSaleResponse(sale);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleReportResponse> getCustomerSales(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }

        return saleProcedureRepository.findCustomerSales(customerId)
                .stream()
                .map(this::toSaleReportResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleReportResponse> getSaleReport() {
        return saleRepository.findSaleReportView()
                .stream()
                .map(this::toSaleReportResponse)
                .toList();
    }

    private Sale findSaleOrThrow(Integer saleId) {
        return saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + saleId));
    }

    private Sale findFreshSaleOrThrow(Integer saleId) {
        entityManager.clear();
        return findSaleOrThrow(saleId);
    }

    private SaleResponse toSaleResponse(Sale sale) {
        Customer customer = sale.getCustomer();
        Employee employee = sale.getEmployee();

        return new SaleResponse(
                sale.getSaleId(),
                customer.getCustomerId(),
                customer.getFirstName() + " " + customer.getLastName(),
                employee.getEmployeeId(),
                employee.getFirstName() + " " + employee.getLastName(),
                sale.getSaleDate(),
                sale.getTotalAmount(),
                sale.getSaleStatus()
        );
    }

    private SaleReportResponse toSaleReportResponse(SaleReportProjection projection) {
        return new SaleReportResponse(
                projection.getSaleId(),
                projection.getSaleDate(),
                projection.getSaleStatus(),
                projection.getTotalAmount(),
                projection.getCustomerId(),
                projection.getCustomerName(),
                projection.getEmployeeId(),
                projection.getEmployeeName(),
                projection.getProductId(),
                projection.getProductName(),
                projection.getBrand(),
                projection.getQuantity(),
                projection.getUnitPrice(),
                projection.getLineTotal()
        );
    }

    private SaleReportResponse toSaleReportResponse(SaleReportRow row) {
        return new SaleReportResponse(
                row.saleId(),
                row.saleDate(),
                row.saleStatus(),
                row.totalAmount(),
                row.customerId(),
                row.customerName(),
                row.employeeId(),
                row.employeeName(),
                row.productId(),
                row.productName(),
                row.brand(),
                row.quantity(),
                row.unitPrice(),
                row.lineTotal()
        );
    }
}
