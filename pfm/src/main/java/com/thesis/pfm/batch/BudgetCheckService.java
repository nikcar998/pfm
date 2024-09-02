package com.thesis.pfm.batch;

import com.thesis.pfm.model.CustomerBudget;
import com.thesis.pfm.model.Transaction;
import com.thesis.pfm.repository.CustomerBudgetRepository;
import com.thesis.pfm.repository.mockBank.TransactionRepository;
import com.thesis.pfm.service.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BudgetCheckService {

    @Autowired
    private CustomerBudgetRepository customerBudgetRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    @Scheduled(cron = "0 0 2 * * ?")
    public void checkBudgetsAndNotify() {
        List<CustomerBudget> customerBudgets = customerBudgetRepository.findAll();

        for (CustomerBudget customerBudget : customerBudgets) {
            // Utilizza findTransactionsByCustomerAndCategory per ottenere tutte le transazioni rilevanti
            List<Transaction> transactions = transactionRepository.findTransactionsByCustomerAndCategory(
                    customerBudget.getCustomerEmail(),
                    customerBudget.getCategoryId()
            );

            // Calcola la somma delle transazioni
            BigDecimal totalSpent = transactions.stream()
                    .map(Transaction::getImporto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Verifica se la spesa totale supera il limite impostato
            if (totalSpent.compareTo(customerBudget.getAmount()) < 0) {
                emailService.sendSimpleEmail(
                        customerBudget.getCustomerEmail(),
                        "Spending Limit Exceeded",
                        String.format("You have exceeded your spending limit for the category '%s'. Total Spent: $%.2f. Limit: $%.2f",
                                customerBudget.getCategory().getName(), totalSpent, customerBudget.getAmount())
                );
            }
        }
    }
}
