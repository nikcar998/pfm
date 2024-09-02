package com.thesis.pfm.service.mockBank;

import com.thesis.pfm.model.Customer;
import com.thesis.pfm.model.Account;
import com.thesis.pfm.model.Transaction;
import com.thesis.pfm.model.mockBank.BankCustomer;
import com.thesis.pfm.repository.mockBank.AccountRepository;
import com.thesis.pfm.repository.mockBank.TransactionRepository;
import com.thesis.pfm.service.TransactionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.thesis.pfm.batch.AverageSpendingBatchJob;

import com.thesis.pfm.utils.CreateData;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AverageSpendingBatchJob averageSpendingBatchJob;

    @Autowired
    private TransactionCategoryService transactionCategoryService;

    @Autowired
    private CreateData createData;

    @Override
    public void run(String... args) throws Exception {
        // Mock data for BankCustomer

        Customer customer = createData.createCustomer();

        BankCustomer bankCustomer = createData.createBankCustomer();

        createData.createAccounts(customer).get(0);

//        createTransactions(account1);
//        addSingleTransaction(account1);

        createData.createTransactionCategories();

        Account account = accountRepository.findByCustomer_Email("nicolocarrozza98@gmail.com").get(0);
        Transaction transaction1 = transactionRepository.findByAccount_NumeroContoCorrente(account.getNumeroContoCorrente()).isEmpty() ?
                transactionRepository.findByAccount_NumeroContoCorrente(account.getNumeroContoCorrente()).get(0) : null;
        createData.addTransactionsWithDescription(account, null, null, (transaction1 == null));

        Account account1 = accountRepository.findByCustomer_Email("nicolocarrozza98@gmail.com").get(1);
        Transaction transaction2 = transactionRepository.findByAccount_NumeroContoCorrente(account1.getNumeroContoCorrente()).isEmpty() ?
                                transactionRepository.findByAccount_NumeroContoCorrente(account1.getNumeroContoCorrente()).get(0) : null ;
        createData.addTransactionsWithDescription(account1, "telefono", null, (transaction2 == null));

        transactionCategoryService.categorizeTransactions(transactionRepository.findAll());


        averageSpendingBatchJob.executeBatchJob();

    }

}
