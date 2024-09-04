package com.thesis.pfm.service;

import com.thesis.pfm.model.Customer;
import com.thesis.pfm.model.CustomerBudget;
import com.thesis.pfm.model.TransactionCategory;
import com.thesis.pfm.repository.CustomerBudgetRepository;
import com.thesis.pfm.service.TransactionCategoryService;
import com.thesis.pfm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerBudgetService {

    @Autowired
    private CustomerBudgetRepository customerBudgetRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionCategoryService transactionCategoryService;

    // Metodo per creare o aggiornare un budget
    public CustomerBudget createOrUpdateBudget(String customerEmail, int categoryId, BigDecimal amount) {
        CustomerBudget existingBudget = customerBudgetRepository.findByCustomerEmailAndCategoryId(customerEmail, categoryId);

        if (existingBudget != null) {
            // Aggiorna il budget esistente
            existingBudget.setAmount(amount);
            return customerBudgetRepository.save(existingBudget);
        } else {
            // Crea un nuovo budget
            CustomerBudget newBudget = new CustomerBudget();
            newBudget.setCustomerEmail(customerEmail);
            newBudget.setCategoryId(categoryId);
            newBudget.setAmount(amount);
            return customerBudgetRepository.save(newBudget);
        }
    }

    public void deleteBudget(String customerEmail, int categoryId) {
        CustomerBudget budget = customerBudgetRepository.findByCustomerEmailAndCategoryId(customerEmail, categoryId);
        if (budget == null) {
            throw new IllegalArgumentException("Budget not found");
        }
        customerBudgetRepository.delete(budget);
    }


    // Metodo per trovare il cliente
    public Customer findCustomerByEmail(String token) {
        return customerService.getCustomerByEmail(token).stream().findFirst().orElse(null);
    }

    // Metodo per trovare una categoria di transazione per ID
    public TransactionCategory findCategoryById(int categoryId) {
        return transactionCategoryService.getCategoryById(categoryId);
    }
    public List<CustomerBudget> getCustomerBudgetsByEmail(String customerEmail) {
        return customerBudgetRepository.findByCustomerEmail(customerEmail);
    }
}
