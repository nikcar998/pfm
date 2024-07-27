package com.thesis.pfm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
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
}
