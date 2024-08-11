package com.thesis.pfm.service.mockBank;

import com.thesis.pfm.controller.dto.MockBankLoginDto;
import com.thesis.pfm.controller.mockBank.BankController;
import com.thesis.pfm.model.mockBank.Account;
import com.thesis.pfm.model.mockBank.Transaction;
import com.thesis.pfm.model.mockBank.BankCustomer;
import com.thesis.pfm.repository.mockBank.AccountRepository;
import com.thesis.pfm.repository.mockBank.TransactionRepository;
import com.thesis.pfm.repository.mockBank.BankCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.thesis.pfm.batch.AverageSpendingBatchJob;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankCustomerRepository bankCustomerRepository;

    @Autowired
    private AverageSpendingBatchJob batchJob;


    @Override
    public void run(String... args) throws Exception {
        // Mock data for BankCustomer
        batchJob.executeBatchJob();
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
        account1.setName("Conto Attico");
        account1.setCashAccountType("CHECKING");
        account1.setAccountStatus("ACTIVE");
        account1.setBic("BIC12345");
        account1.setOwnerName("User One");
        account1.setCustomer(bankCustomer1);
        account1.setBalance(new BigDecimal("1534.35"));
        account1.setNumeroContoCorrente("1000/000005512");
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
        account2.setBalance(new BigDecimal("104.88"));
        account2.setNumeroContoCorrente("1000/000005814");
        accountRepository.save(account2);

        /*
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
        account1.setBalance(new BigDecimal("2530.55"));
        accountRepository.save(account3);

  /////////////////

 */
        // Mock data for Transactions
        // Supponendo che `account1` sia già stato creato e disponibile
        List<Transaction> transactionList = new ArrayList<>();

        Transaction transaction1 = new Transaction();
        transaction1.setDataPresaInCarico(LocalDate.now());
        transaction1.setDataEsecuzione(LocalDate.now().plusDays(1));
        transaction1.setNumeroOrdine("INTER201928334405");
        transaction1.setOrdinante("Carrozza Nicolo");
        transaction1.setFiliale("Milano");
        transaction1.setBeneficiario("Tizio Caio");
        transaction1.setIndirizzo("");
        transaction1.setLocalita("");
        transaction1.setPaese("");
        transaction1.setIban("IT60X054281110100000024356");
        transaction1.setBic("BPMOIT22XXX");
        transaction1.setDebitoreEffettivo("");
        transaction1.setCreditoreEffettivo("");
        transaction1.setIdentificativoBonifico("");
        transaction1.setTipologia("Bonifico");
        transaction1.setBancaBeneficiario("Unicredit");
        transaction1.setDescrizione("Regalo di compleanno");
        transaction1.setImporto(new BigDecimal(100.0));
        transaction1.setTRN("0321243443923423423333100T");
        transaction1.setCommissioni(new BigDecimal(00.10));
        transaction1.setTotaleOperazione(new BigDecimal(100.10));
        transaction1.setAccount(account1);
        transactionList.add(transaction1);

// Esempio per generare 40 transazioni simili
        for (int i = 2; i <= 280; i++) {
            Transaction transaction = new Transaction();
            transaction.setDataPresaInCarico(LocalDate.now().minusDays(i));
            transaction.setDataEsecuzione(LocalDate.now().minusDays(i-1));
            transaction.setNumeroOrdine("INTER2019283344" + (i * 100 + 5));
            transaction.setOrdinante("Ordinante " + i);
            transaction.setFiliale("Filiale " + i);
            transaction.setBeneficiario("Beneficiario " + i);
            transaction.setIndirizzo("Indirizzo " + i);
            transaction.setLocalita("Località " + i);
            transaction.setPaese("Paese " + i);
            transaction.setIban("IT60X05428111010000002435" + i);
            transaction.setBic("BICCODE" + i);
            transaction.setDebitoreEffettivo("Debitore " + i);
            transaction.setCreditoreEffettivo("Creditore " + i);
            transaction.setIdentificativoBonifico("IDBONIFICO" + i);
            transaction.setTipologia("Bonifico");
            transaction.setBancaBeneficiario("Banca " + i);
            transaction.setDescrizione("Descrizione " + i);
            transaction.setImporto(new BigDecimal(100.0 + i));
            transaction.setTRN("TRN1234567890" + i);
            transaction.setCommissioni(new BigDecimal(0.10 + (i * 0.01)));
            transaction.setTotaleOperazione(new BigDecimal(100.0 + i + 0.10 + (i * 0.01)));
            transaction.setAccount(account1);
            transactionList.add(transaction);
        }
        for (int i = 2; i <= 190; i++) {
            Transaction transaction = new Transaction();
            transaction.setDataPresaInCarico(LocalDate.now().minusDays(i));
            transaction.setDataEsecuzione(LocalDate.now().minusDays(i-1));
            transaction.setNumeroOrdine("INTER2019283344" + (i * 100 + 5));
            transaction.setOrdinante("Ordinante " + i);
            transaction.setFiliale("Filiale " + i);
            transaction.setBeneficiario("Beneficiario " + i);
            transaction.setIndirizzo("Indirizzo " + i);
            transaction.setLocalita("Località " + i);
            transaction.setPaese("Paese " + i);
            transaction.setIban("IT60X05428111010000002435" + i);
            transaction.setBic("BICCODE" + i);
            transaction.setDebitoreEffettivo("Debitore " + i);
            transaction.setCreditoreEffettivo("Creditore " + i);
            transaction.setIdentificativoBonifico("IDBONIFICO" + i);
            transaction.setTipologia("Bonifico");
            transaction.setBancaBeneficiario("Banca " + i);
            transaction.setDescrizione("Descrizione " + i);
            transaction.setImporto(new BigDecimal(-199.0 + i));
            transaction.setTRN("TRN1234567890" + i);
            transaction.setTotaleOperazione(new BigDecimal(-199.0 + i ));
            transaction.setAccount(account1);
            transactionList.add(transaction);
        }

// Salvataggio di tutte le transazioni nel repository
        transactionRepository.saveAll(transactionList);



        //////////////////////////////////////////////////////////////////////////////
    }
}
