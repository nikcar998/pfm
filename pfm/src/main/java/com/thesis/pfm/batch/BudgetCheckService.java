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
import java.time.LocalDate;
import java.time.YearMonth;
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

        // Calcola l'inizio e la fine del mese corrente
        LocalDate startOfMonth = YearMonth.now().atDay(1);
        LocalDate endOfMonth = YearMonth.now().atEndOfMonth();

        for (CustomerBudget customerBudget : customerBudgets) {
            // Utilizza il nuovo metodo del repository per ottenere solo le transazioni del mese corrente
            List<Transaction> transactions = transactionRepository.findTransactionsByCustomerAndCategoryAndDateBetween(
                    customerBudget.getCustomerEmail(),
                    customerBudget.getCategoryId(),
                    startOfMonth,
                    endOfMonth
            );

            // Calcola la somma delle transazioni per il mese corrente
            BigDecimal totalSpent = transactions.stream()
                    .map(Transaction::getImporto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Verifica se la spesa totale supera il limite impostato
            if (totalSpent.compareTo(customerBudget.getAmount()) < 0) {
                emailService.sendSimpleEmail(
                        customerBudget.getCustomerEmail(),
                        "Spending Limit Exceeded",
                        String.format("You have exceeded your spending limit for the category '%s'. Total Spent: $%.2f. Limit: $%.2f",
                                customerBudget.getCategoryId(), totalSpent, customerBudget.getAmount())
                );
            }
        }
    }
}
