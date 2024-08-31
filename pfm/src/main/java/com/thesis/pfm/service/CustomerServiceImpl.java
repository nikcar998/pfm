package com.thesis.pfm.service;

import com.thesis.pfm.model.Customer;
import com.thesis.pfm.model.mockBank.BankCustomer;
import com.thesis.pfm.repository.CustomerRepository;
import com.thesis.pfm.repository.mockBank.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllStudents() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Async
    public CompletableFuture<Void> updateCustomerTransactionAverages(int customerId) {
        return CompletableFuture.runAsync(() -> {
            // Trova il Customer tramite ID
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

                // Calcola la media delle spese (transazioni negative) degli ultimi mesi
                BigDecimal averageExpance = transactionRepository.findAverageExpanceLastMonths(
                        customer.getEmail(), LocalDate.now().minusMonths(3));

                // Calcola la media degli incassi (transazioni positive) degli ultimi mesi
                BigDecimal averageIncome = transactionRepository.findAverageIncomeLastMonths(
                        customer.getEmail(), LocalDate.now().minusMonths(3));

                // Imposta le medie nei campi del Customer
                customer.setAverageExpance(averageExpance != null ? averageExpance.intValue() : null);
                customer.setAverageIncome(averageIncome != null ? averageIncome.intValue() : null);

                // Salva il Customer aggiornato
                customerRepository.save(customer);
        });
    }
}
