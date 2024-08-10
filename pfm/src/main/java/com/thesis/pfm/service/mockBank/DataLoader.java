package com.thesis.pfm.service.mockBank;

import com.thesis.pfm.model.mockBank.Account;
import com.thesis.pfm.model.mockBank.Transaction;
import com.thesis.pfm.model.mockBank.BankCustomer;
import com.thesis.pfm.repository.mockBank.AccountRepository;
import com.thesis.pfm.repository.mockBank.TransactionRepository;
import com.thesis.pfm.repository.mockBank.BankCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankCustomerRepository bankCustomerRepository;

    @Override
    public void run(String... args) throws Exception {
        // Mock data for BankCustomer
        if(bankCustomerRepository.findById("user1").isPresent()){
            return;
        }
        BankCustomer bankCustomer1 = new BankCustomer();
        bankCustomer1.setUsername("user1");
        bankCustomer1.setPassword("password1");
        bankCustomer1.setSessionId("session1");
        bankCustomer1.setSessionExpiryDate(LocalDate.now().plusDays(1));
        bankCustomerRepository.save(bankCustomer1);

        BankCustomer bankCustomer2 = new BankCustomer();
        bankCustomer2.setUsername("user2");
        bankCustomer2.setPassword("password2");
        bankCustomer2.setSessionId("session2");
        bankCustomer2.setSessionExpiryDate(LocalDate.now().plusDays(1));
        bankCustomerRepository.save(bankCustomer2);

        // Mock data for Accounts
        Account account1 = new Account();
        account1.setIban("IT60X0542811101000000123456");
        account1.setBban("BBAN123");
        account1.setPan("1234567812345678");
        account1.setMaskedPan("1234********5678");
        account1.setCurrency("EUR");
        account1.setName("Primary Account");
        account1.setCashAccountType("CHECKING");
        account1.setAccountStatus("ACTIVE");
        account1.setBic("BIC12345");
        account1.setOwnerName("User One");
        account1.setCustomer(bankCustomer1);
        accountRepository.save(account1);

        Account account2 = new Account();
        account2.setIban("IT60X0542811101000000654321");
        account2.setBban("BBAN456");
        account2.setPan("8765432187654321");
        account2.setMaskedPan("8765********4321");
        account2.setCurrency("EUR");
        account2.setName("Savings Account");
        account2.setCashAccountType("SAVINGS");
        account2.setAccountStatus("ACTIVE");
        account2.setBic("BIC54321");
        account2.setOwnerName("User One");
        account2.setCustomer(bankCustomer1);
        accountRepository.save(account2);

        Account account3 = new Account();
        account3.setIban("IT60X0542811101000000987654");
        account3.setBban("BBAN789");
        account3.setPan("5678123456781234");
        account3.setMaskedPan("5678********1234");
        account3.setCurrency("EUR");
        account3.setName("Investment Account");
        account3.setCashAccountType("INVESTMENT");
        account3.setAccountStatus("ACTIVE");
        account3.setBic("BIC98765");
        account3.setOwnerName("User Two");
        account3.setCustomer(bankCustomer2);
        accountRepository.save(account3);

        // Mock data for Transactions
        Transaction transaction1 = new Transaction();
        transaction1.setDataPresaInCarico(LocalDate.now());
        transaction1.setDataEsecuzione(LocalDate.now().plusDays(1));
        transaction1.setNumeroOrdine("Order123");
        transaction1.setNumeroContoCorrente("Account123");
        transaction1.setOrdinante("Ordinante");
        transaction1.setFiliale("Filiale");
        transaction1.setBeneficiario("Beneficiario");
        transaction1.setIndirizzo("Indirizzo");
        transaction1.setLocalita("Localita");
        transaction1.setPaese("Paese");
        transaction1.setIban("IT60X0542811101000000123456");
        transaction1.setBic("BIC12345");
        transaction1.setDebitoreEffettivo("Debitore Effettivo");
        transaction1.setCreditoreEffettivo("Creditore Effettivo");
        transaction1.setIdentificativoBonifico("Identificativo Bonifico");
        transaction1.setTipologia("Tipologia");
        transaction1.setBancaBeneficiario("Banca Beneficiario");
        transaction1.setDescrizione("Descrizione");
        transaction1.setImporto(1000.0);
        transaction1.setTRN("TRN12345");
        transaction1.setCommissioni(10.0);
        transaction1.setTotaleOperazione(1010.0);
        transaction1.setAccount(account1);
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setDataPresaInCarico(LocalDate.now().minusDays(2));
        transaction2.setDataEsecuzione(LocalDate.now().minusDays(1));
        transaction2.setNumeroOrdine("Order456");
        transaction2.setNumeroContoCorrente("Account456");
        transaction2.setOrdinante("Ordinante");
        transaction2.setFiliale("Filiale");
        transaction2.setBeneficiario("Beneficiario");
        transaction2.setIndirizzo("Indirizzo");
        transaction2.setLocalita("Localita");
        transaction2.setPaese("Paese");
        transaction2.setIban("IT60X0542811101000000654321");
        transaction2.setBic("BIC54321");
        transaction2.setDebitoreEffettivo("Debitore Effettivo");
        transaction2.setCreditoreEffettivo("Creditore Effettivo");
        transaction2.setIdentificativoBonifico("Identificativo Bonifico");
        transaction2.setTipologia("Tipologia");
        transaction2.setBancaBeneficiario("Banca Beneficiario");
        transaction2.setDescrizione("Descrizione");
        transaction2.setImporto(2000.0);
        transaction2.setTRN("TRN54321");
        transaction2.setCommissioni(20.0);
        transaction2.setTotaleOperazione(2020.0);
        transaction2.setAccount(account2);
        transactionRepository.save(transaction2);
    }
}
