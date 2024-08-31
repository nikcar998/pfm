package com.thesis.pfm.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.thesis.pfm.model.mockBank.Account;
import com.thesis.pfm.model.mockBank.BankCustomer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    private String email;

    private String name;
    private String surname;
    private Date bornDate;

    @Column(nullable = true)
    private Integer averageIncome;

    @Column(nullable = true)
    private Integer averageExpance;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Account> accounts;
}
