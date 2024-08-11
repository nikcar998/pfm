package com.thesis.pfm.batch;

import com.thesis.pfm.model.Customer;
import com.thesis.pfm.repository.CustomerRepository;
import com.thesis.pfm.service.CustomerService;
import com.thesis.pfm.service.CustomerServiceImpl;
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
                .map(customer -> customerService.updateCustomerTransactionAverages(customer.getCustomerId()))
                .toList();

        // Aspetta che tutte le operazioni async siano completate
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
