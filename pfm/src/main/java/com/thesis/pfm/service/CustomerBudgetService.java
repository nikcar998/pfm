package com.thesis.pfm.service;

import com.thesis.pfm.model.CustomerBudget;
import com.thesis.pfm.repository.CustomerBudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CustomerBudgetService {

    @Autowired
    private CustomerBudgetRepository customerBudgetRepository;

    // Metodo per creare o aggiornare un budget
    public CustomerBudget createOrUpdateBudget(String customerEmail, Long categoryId, BigDecimal amount) {
        CustomerBudget existingBudget = customerBudgetRepository.findByCustomerEmailAndCategoryId(customerEmail, categoryId);

        if (existingBudget != null) {
            // Aggiorna il budget esistente
            existingBudget.setAmount(amount); // Utilizza il metodo corretto per impostare l'importo
            return customerBudgetRepository.save(existingBudget);
        } else {
            // Crea un nuovo budget
            CustomerBudget newBudget = new CustomerBudget();
            newBudget.setCustomerEmail(customerEmail);
            newBudget.setCategoryId(categoryId);
            newBudget.setAmount(amount); // Utilizza il metodo corretto per impostare l'importo
            return customerBudgetRepository.save(newBudget);
        }
    }
}
