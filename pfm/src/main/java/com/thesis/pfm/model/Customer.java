package com.thesis.pfm.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.thesis.pfm.model.mockBank.BankCustomer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    private String name;
    private String surname;
    private String email;
    private Date bornDate;

    @Column(nullable = true)
    private String job;

    @Column(nullable = true)
    private Integer familyMembers;

    @Column(nullable = true)
    private Integer averageIncome;

    @Column(nullable = true)
    private Integer averageExpance;

    @OneToOne
    @JoinColumn(name = "bank_customer_username")
    @JsonManagedReference
    private BankCustomer bankCustomer;
}
