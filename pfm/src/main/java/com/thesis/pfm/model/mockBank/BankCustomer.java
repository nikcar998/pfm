package com.thesis.pfm.model.mockBank;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.thesis.pfm.model.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class BankCustomer {
    @Id
    private String username;
    private String password;
    private String sessionId;
    private LocalDate sessionExpiryDate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Account> accounts;

    @OneToOne(mappedBy = "bankCustomer")
    private Customer customer;
}
