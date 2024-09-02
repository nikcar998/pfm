package com.thesis.pfm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CustomerBudgetId.class) // Utilizza una classe composta come chiave primaria
public class CustomerBudget {

    @Id
    @Column(name = "customer_email")
    private String customerEmail; // chiave primaria

    @Id
    @Column(name = "category_id")
    private Long categoryId; // chiave primaria

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_email", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private TransactionCategory category;

    @Column(nullable = false)
    private BigDecimal amount; // Quantità limite per il budget
}
