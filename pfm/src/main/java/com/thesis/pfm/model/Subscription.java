package com.thesis.pfm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Subscription {
    @Id
    private int id;
    private String description;
    private String type;
    private BigDecimal amount;

    @ManyToOne(optional = false)  // Relazione obbligatoria con Customer
    @JoinColumn(name = "customer_email", nullable = false)  // Foreign key non nullable
    @JsonBackReference
    private Customer customer;

    // Relazione Many-to-One con TransactionCategory
    @ManyToOne
    @JoinColumn(name = "transaction_category_id", nullable = false)
    private TransactionCategory transactionCategory;
}
