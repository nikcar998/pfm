package com.thesis.pfm.repository;

import com.thesis.pfm.model.CustomerBudget;
import com.thesis.pfm.model.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerBudgetRepository extends JpaRepository<CustomerBudget, Long> {

    // Metodo per trovare un budget esistente per una combinazione di cliente e categoria
    CustomerBudget findByCustomerEmailAndCategoryId(String customerEmail, Long categoryId);

}
