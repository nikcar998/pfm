package com.thesis.pfm.model.mockBank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.thesis.pfm.model.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
@Entity
public class Account {
    @Id
    private String numeroContoCorrente;

    @Column(nullable = false)
    private String iban;

    @Column(nullable = false)
    private String bban;

    @Column(nullable = false)
    private String pan;

    private String maskedPan;

    @Column(nullable = false)
    private String currency;

    private String name;

    private String cashAccountType;

    private String accountStatus;

    private String bic;

    private String ownerName;

    @Column(precision = 19, scale = 4)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "customer_email", nullable = false)
    @JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Transaction> transactions;
}
