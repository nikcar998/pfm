package com.thesis.pfm.service.mockBank;

import com.thesis.pfm.model.Customer;
import com.thesis.pfm.model.mockBank.Account;
import com.thesis.pfm.model.mockBank.BankCustomer;
import com.thesis.pfm.model.mockBank.Transaction;
import com.thesis.pfm.repository.mockBank.AccountRepository;
import com.thesis.pfm.repository.mockBank.BankCustomerRepository;
import com.thesis.pfm.repository.mockBank.TransactionRepository;
import com.thesis.pfm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public BankCustomer authenticateAndAddBankCustomer(String username, String password, Customer customer) {
        BankCustomer bankCustomer = bankCustomerRepository.findById(username).orElse(null);
        if (bankCustomer != null && bankCustomer.getPassword().equals(password)) {
            UUID randomUUID = UUID.randomUUID();
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'.000Z'");
            bankCustomer.setSessionId(randomUUID.toString().replaceAll("_", "") + sdfSource.format(new Date()));
            bankCustomer.setSessionExpiryDate(LocalDate.now().plusDays(1));
            bankCustomerRepository.save(bankCustomer);
            customer.setBankCustomer(bankCustomer);
            customerservice.saveCustomer(customer);
            return bankCustomer;
        }
        return null;
    }

    public List<Account> getAllAccounts(Optional<BankCustomer> customer) {
        return customer.map(bankCustomer -> accountRepository.findByCustomer_Username(bankCustomer.getUsername())).orElse(null);
    }

    public List<Transaction> getAllTransactions(Optional<BankCustomer> customer, String iban) {
        if (customer.isPresent()) {
            BankCustomer bankCustomer = customer.get();
            Optional<Account> account = accountRepository.findById(iban);
            if (account.isPresent() && account.get().getCustomer().getUsername().equals(bankCustomer.getUsername())) {
                return transactionRepository.findByAccount_NumeroContoCorrente(iban);
            }
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
}
