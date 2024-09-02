package com.thesis.pfm.service.mockBank;

import com.thesis.pfm.model.Customer;
import com.thesis.pfm.model.Account;
import com.thesis.pfm.model.TransactionCategory;
import com.thesis.pfm.model.mockBank.BankCustomer;
import com.thesis.pfm.model.Transaction;
import com.thesis.pfm.repository.TransactionCategoryRepository;
import com.thesis.pfm.repository.mockBank.AccountRepository;
import com.thesis.pfm.repository.mockBank.BankCustomerRepository;
import com.thesis.pfm.repository.mockBank.TransactionRepository;
import com.thesis.pfm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class BankService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankCustomerRepository bankCustomerRepository;

    @Autowired
    private CustomerService customerservice;

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;


    public BankCustomer authenticate(String username, String password) {
        BankCustomer customer = bankCustomerRepository.findById(username).orElse(null);
        if (customer != null && customer.getPassword().equals(password)) {
            UUID randomUUID = UUID.randomUUID();
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'.000Z'");
            customer.setSessionId(randomUUID.toString().replaceAll("_", "") + sdfSource.format(new Date()));
            customer.setSessionExpiryDate(LocalDate.now().plusDays(1));
            bankCustomerRepository.save(customer);
            return customer;
        }
        return null;
    }
//    public BankCustomer authenticateAndAddBankCustomer(String username, String password, Customer customer) {
//        BankCustomer bankCustomer = bankCustomerRepository.findById(username).orElse(null);
//        if (bankCustomer != null && bankCustomer.getPassword().equals(password)) {
//            UUID randomUUID = UUID.randomUUID();
//            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'.000Z'");
//            bankCustomer.setSessionId(randomUUID.toString().replaceAll("_", "") + sdfSource.format(new Date()));
//            bankCustomer.setSessionExpiryDate(LocalDate.now().plusDays(1));
//            bankCustomerRepository.save(bankCustomer);
//            customer.setBankCustomer(bankCustomer);
//            customerservice.saveCustomer(customer);
//            return bankCustomer;
//        }
//        return null;
//    }

    public List<Account> getAllAccounts(Customer customer) {
        return accountRepository.findByCustomer_Email(customer.getEmail());
    }

    public List<Transaction> getAllTransactions(Customer customer, String accountCode) {

        Optional<Account> account = accountRepository.findById(accountCode);
        if (account.isPresent() && account.get().getCustomer().getEmail().equals(customer.getEmail())) {
            return transactionRepository.findByAccount_NumeroContoCorrente(accountCode);
        }

        return null;
    }

    public Optional<BankCustomer> getCustomerBySessionId(String sessionId) {
        Optional<BankCustomer> customer = bankCustomerRepository.findBySessionId(sessionId);
        if (customer.isEmpty() || customer.get().getSessionExpiryDate().isBefore(LocalDate.now())) {
            return Optional.empty();
        }
        return customer;
    }

    public List<Transaction> getTransactionsByAccountAndMonth(Customer customer, String accountCode, int year, int month) {
        Optional<Account> account = accountRepository.findById(accountCode);
        if (account.isPresent() && account.get().getCustomer().getEmail().equals(customer.getEmail())) {
            return transactionRepository.findByAccountAndMonth(accountCode, year, month);
        }
//      categorizeTransactions(Arrays.asList(new Transaction()));
        return null;
    }


    /////////////////////////codice mock per esempio ////////////////////

    private double calculateCategoryScore(Transaction trn, TransactionCategory cat) {
        return 100;
    }

    public void categorizeTransactions(List<Transaction> transactions) {
        List<TransactionCategory> categories = transactionCategoryRepository.findAll();

        for (Transaction transaction : transactions) {
            TransactionCategory bestCategory = null;
            double highestScore = 0.0;

            for (TransactionCategory category : categories) {
                double score = calculateCategoryScore(transaction, category);

                if (score > highestScore) {
                    highestScore = score;
                    bestCategory = category;
                }
            }

            if (bestCategory != null) {
                transaction.setTransactionCategory(bestCategory);
                transactionRepository.save(transaction);
            }
        }
    }
}

