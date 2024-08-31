package com.thesis.pfm.service;

import com.thesis.pfm.model.Customer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CustomerService {
    public Customer saveCustomer(Customer customer);
    public List<Customer> getAllStudents();
    public List<Customer> getCustomerByEmail(String email);
    CompletableFuture<Void> updateCustomerTransactionAverages(String customerEmail);
}
