package com.phucx.phucxfoodshop.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.phucx.phucxfoodshop.constant.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Immutable
@Data @ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {
    @Id
    private String orderID;
    private BigDecimal freight;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private BigDecimal extendedPrice;

    // @Id
    private String discountID;
    private Integer discountPercent;

    // @Id
    private Integer productID;
    private BigDecimal unitPrice;
    private Integer quantity;

    private String customerID;
    private String employeeID;
    private Integer shipperID;

    private String shipName;
    private String shipAddress;
    private String shipCity;
    private String shipDistrict;
    private String shipWard;
    private String phone;
    
    private String paymentMethod;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requiredDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime shippedDate;
}
