package com.thesis.pfm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    private String name;
    private String surname;
    private String email;
    private String job;
    private String bornDate;
    private int familyMembers;
    private int averageIncome;
    private int averageExpance;
}
