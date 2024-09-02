package com.thesis.pfm;

import com.thesis.pfm.model.Account;
import com.thesis.pfm.model.Customer;
import com.thesis.pfm.model.Transaction;
import com.thesis.pfm.model.mockBank.BankCustomer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BankCustomerResource {
    private BankCustomer bankCustomer;
    private List<Account> accountList;
    private List<Transaction> transactionList;
    private Customer customer;
}
