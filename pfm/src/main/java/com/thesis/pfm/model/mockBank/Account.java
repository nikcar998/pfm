package com.thesis.pfm.model.mockBank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Data
@Entity
public class Account {
    @Id
    private String iban;
    private String bban;
    private String pan;
    private String maskedPan;
    private String currency;
    private String name;
    private String cashAccountType;
    private String accountStatus;
    private String bic;
    private String ownerName;


    @ManyToOne
    @JoinColumn(name = "customer_username", nullable = false)
    @JsonBackReference
    private BankCustomer customer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Transaction> transactions;
}

