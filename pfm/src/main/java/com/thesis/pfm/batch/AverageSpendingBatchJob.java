package com.thesis.pfm.batch;

import com.thesis.pfm.model.Customer;
import com.thesis.pfm.repository.CustomerRepository;
import com.thesis.pfm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class AverageSpendingBatchJob {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Scheduled(cron = "0 0 0 * * ?") // Esegui ogni giorno a mezzanotte
    public void updateAllCustomerAverages() {
        executeBatchJob();
    }

    public void executeBatchJob() {
        List<Customer> customers = customerRepository.findAll();
        List<CompletableFuture<Void>> futures = customers.stream()
                .map(customer -> customerService.updateCustomerTransactionAverages(customer.getEmail()))
                .toList();

        // Aspetta che tutte le operazioni async siano completate
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
//
//
//    ///////////////// PER ESEMPIO ///////////////////////7
//
//    @Data
//    private class CustomerBudget {
//        private String customerEmail;
//        private Category category;
//        private BigDecimal categoryAmount;
//        @Data
//        public class Category{
//            private Long id;
//            private String name;
//        }
//    }
//
//    private class CustomerBudgetRepository {
//        public List<CustomerBudget> findAll() {
//            return Arrays.asList(new CustomerBudget());
//        }
//    }
//
//    private class EmailService {
//        public void sendSimpleEmail(String to, String subject, String text){}
//    }
//    CustomerBudgetRepository customerBudgetRepository = new CustomerBudgetRepository();
//    EmailService emailService = new EmailService();
//    @Autowired
//    TransactionRepository transactionRepository;
//
//    @Scheduled(cron = "0 0 2 * * ?")
//    public void checkBudgetsAndNotify() {
//        List<CustomerBudget> customerBudgets = customerBudgetRepository.findAll();
//
//        for (CustomerBudget customerBudget : customerBudgets) {
//            BigDecimal totalSpent = transactionRepository.sumByCustomerAndCategory(
//                    customerBudget.getCustomerEmail(),
//                    customerBudget.getCategory().getId()
//            );
//
//            if (totalSpent.compareTo(customerBudget.getCategoryAmount()) > 0) {
//                emailService.sendSimpleEmail(
//                        customerBudget.getCustomerEmail(),
//                        "Spending Limit Exceeded",
//                        String.format("You have exceeded your spending limit for the category '%s'. Total Spent: $%.2f. Limit: $%.2f",
//                                customerBudget.getCategory().getName(), totalSpent, customerBudget.getCategoryAmount())
//                );
//            }
//        }
//    }
}
