package com.thesis.pfm.service.mockBank;

import com.thesis.pfm.model.Customer;
import com.thesis.pfm.model.Account;
import com.thesis.pfm.model.Transaction;
import com.thesis.pfm.model.TransactionCategory;
import com.thesis.pfm.model.mockBank.BankCustomer;
import com.thesis.pfm.repository.CustomerRepository;
import com.thesis.pfm.repository.TransactionCategoryRepository;
import com.thesis.pfm.repository.mockBank.AccountRepository;
import com.thesis.pfm.repository.mockBank.TransactionRepository;
import com.thesis.pfm.repository.mockBank.BankCustomerRepository;
import com.thesis.pfm.service.TransactionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.thesis.pfm.batch.AverageSpendingBatchJob;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
    private CustomerRepository customerRepository;

    @Autowired
    private AverageSpendingBatchJob averageSpendingBatchJob;

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    @Autowired
    private TransactionCategoryService transactionCategoryService;


    @Override
    public void run(String... args) throws Exception {
        // Mock data for BankCustomer

        Customer customer = createCustomer();

        BankCustomer bankCustomer = createBankCustomer();

        Account account1 = createAccounts(customer);

//        createTransactions(account1);
//        addSingleTransaction(account1);

        createTransactionCategories();

        addSingleTransaction(accountRepository.findByCustomer_Email("nicolocarrozza98@gmail.com").get(0));

        transactionCategoryService.categorizeTransactions(transactionRepository.findAll());


        averageSpendingBatchJob.executeBatchJob();

    }

    private void createTransactionCategories() {
        List<TransactionCategory> transactionCategory = new ArrayList<>();
        transactionCategory.add(TransactionCategory.builder().name("Acqua").build());
        transactionCategory.add(TransactionCategory.builder().name("Addebiti vari").build());
        transactionCategory.add(TransactionCategory.builder().name("Addebito mia carta di credito").build());
        transactionCategory.add(TransactionCategory.builder().name("Affitti incassati").build());
        transactionCategory.add(TransactionCategory.builder().name("Alimenti coniuge e figli").build());
        transactionCategory.add(TransactionCategory.builder().name("Alimenti coniuge e figli ricevuti").build());
        transactionCategory.add(TransactionCategory.builder().name("Assegni incassati").build());
        transactionCategory.add(TransactionCategory.builder().name("Assegni pagati").build());
        transactionCategory.add(TransactionCategory.builder().name("Assicurazione auto e moto e tasse veicoli").build());
        transactionCategory.add(TransactionCategory.builder().name("Associazioni").build());
        transactionCategory.add(TransactionCategory.builder().name("Bonifici in uscita").build());
        transactionCategory.add(TransactionCategory.builder().name("Bonifici ricevuti").build());
        transactionCategory.add(TransactionCategory.builder().name("Carburanti").build());
        transactionCategory.add(TransactionCategory.builder().name("Casa varie").build());
        transactionCategory.add(TransactionCategory.builder().name("Cellulare").build());
        transactionCategory.add(TransactionCategory.builder().name("Corsi e sport").build());
        transactionCategory.add(TransactionCategory.builder().name("Disinvestimenti, BDR e XME Salvadanaio").build());
        transactionCategory.add(TransactionCategory.builder().name("Domiciliazioni e Utenze").build());
        transactionCategory.add(TransactionCategory.builder().name("Donazioni").build());
        transactionCategory.add(TransactionCategory.builder().name("Entrate varie").build());
        transactionCategory.add(TransactionCategory.builder().name("Erogazione Finanziamento").build());
        transactionCategory.add(TransactionCategory.builder().name("Famiglie varie").build());
        transactionCategory.add(TransactionCategory.builder().name("Farmacia").build());
        transactionCategory.add(TransactionCategory.builder().name("Gas & energia elettrica").build());
        transactionCategory.add(TransactionCategory.builder().name("Generi alimentari e supermercato").build());
        transactionCategory.add(TransactionCategory.builder().name("Giroconti in uscita").build());
        transactionCategory.add(TransactionCategory.builder().name("Giroconto in entrata").build());
        transactionCategory.add(TransactionCategory.builder().name("Imposte sul reddito e tasse varie").build());
        transactionCategory.add(TransactionCategory.builder().name("Imposte, bolli e commissioni").build());
        transactionCategory.add(TransactionCategory.builder().name("Interessi e cedole").build());
        transactionCategory.add(TransactionCategory.builder().name("Investimenti").build());
        transactionCategory.add(TransactionCategory.builder().name("Istruzione").build());
        transactionCategory.add(TransactionCategory.builder().name("Libri, film e musica").build());
        transactionCategory.add(TransactionCategory.builder().name("Manutenzione casa").build());
        transactionCategory.add(TransactionCategory.builder().name("Multe").build());
        transactionCategory.add(TransactionCategory.builder().name("Pagamento affitti").build());
        transactionCategory.add(TransactionCategory.builder().name("Pedaggi e Telepass").build());
        transactionCategory.add(TransactionCategory.builder().name("Polizze").build());
        transactionCategory.add(TransactionCategory.builder().name("Polizze casa").build());
        transactionCategory.add(TransactionCategory.builder().name("Prelievi").build());
        transactionCategory.add(TransactionCategory.builder().name("Prestiti").build());
        transactionCategory.add(TransactionCategory.builder().name("Rate e leasing veicoli").build());
        transactionCategory.add(TransactionCategory.builder().name("Rate Mutuo e Finanziamento").build());
        transactionCategory.add(TransactionCategory.builder().name("Rate piani pensionistici").build());
        transactionCategory.add(TransactionCategory.builder().name("Rate prestiti").build());
        transactionCategory.add(TransactionCategory.builder().name("Regali").build());
        transactionCategory.add(TransactionCategory.builder().name("Regali ricevuti").build());
        transactionCategory.add(TransactionCategory.builder().name("Ricarica carte").build());
        transactionCategory.add(TransactionCategory.builder().name("Rimborsi spese e storni").build());
        transactionCategory.add(TransactionCategory.builder().name("Rimborsi spese mediche").build());
        transactionCategory.add(TransactionCategory.builder().name("Riscaldamento").build());
        transactionCategory.add(TransactionCategory.builder().name("Salute e benessere varie").build());
        transactionCategory.add(TransactionCategory.builder().name("Spese importanti e ristrutturazione").build());
        transactionCategory.add(TransactionCategory.builder().name("Spese mediche").build());
        transactionCategory.add(TransactionCategory.builder().name("Spese per l'infanzia").build());
        transactionCategory.add(TransactionCategory.builder().name("Spettacoli e musei").build());
        transactionCategory.add(TransactionCategory.builder().name("Stipendi e pensioni").build());
        transactionCategory.add(TransactionCategory.builder().name("Storni").build());
        transactionCategory.add(TransactionCategory.builder().name("Tasse sulla casa").build());
        transactionCategory.add(TransactionCategory.builder().name("Tempo libero varie").build());
        transactionCategory.add(TransactionCategory.builder().name("Trasporti varie").build());
        transactionCategory.add(TransactionCategory.builder().name("Trasporti, noleggi, taxi e parcheggi ").build());
        transactionCategory.add(TransactionCategory.builder().name("Treno, aereo, nave").build());
        transactionCategory.add(TransactionCategory.builder().name("TV, Internet, telefono").build());
        transactionCategory.add(TransactionCategory.builder().name("Viaggi e vacanze").build());

        transactionCategoryRepository.saveAll(transactionCategory);

    }

    private Customer createCustomer(){

        if(!customerRepository.findByEmail("nicolocarrozza98@gmail.com").isEmpty()){
            return null;
        }

        Customer customer = new Customer();
        customer.setEmail("nicolocarrozza98@gmail.com");
        customer.setName("nicolo");
        customer.setSurname("carrozza");
        customer.setBornDate(new Date());

        return customerRepository.save(customer);
    }

    private BankCustomer createBankCustomer(){
        if(bankCustomerRepository.findById("user1").isPresent()){
            return null;
        }

//        batchJob.executeBatchJob();
        BankCustomer bankCustomer1 = new BankCustomer();
        bankCustomer1.setUsername("user1");
        bankCustomer1.setPassword("password1");
        bankCustomer1.setSessionId("session1");
        bankCustomer1.setSessionExpiryDate(LocalDate.now().plusDays(1));
        bankCustomerRepository.save(bankCustomer1);
        return bankCustomer1;
    }

    private Account createAccounts(Customer customer){

        if(!accountRepository.findByCustomer_Email("nicolocarrozza98@gmail.com").isEmpty()){
            return null;
        }
        // Mock data for Accounts
        Account account1 = new Account();
        account1.setIban("IT60X0542811101000000123456");
        account1.setBban("BBAN123");
        account1.setPan("1234567812345678");
        account1.setMaskedPan("1234********5678");
        account1.setCurrency("EUR");
        account1.setName("Conto Attico");
        account1.setAccountStatus("ACTIVE");
        account1.setBic("BIC12345");
        account1.setOwnerName("User One");
        account1.setCustomer(customer);
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
        account2.setAccountStatus("ACTIVE");
        account2.setBic("BIC54321");
        account2.setOwnerName("User One");
        account2.setCustomer(customer);
        account2.setBalance(new BigDecimal("104.88"));
        account2.setNumeroContoCorrente("1000/000005814");
        accountRepository.save(account2);

        return account1;
    }

    private void createTransactions(Account account1){

        if(account1 == null){
            return;
        }

        // Mock data for Transactions
        // Supponendo che `account1` sia già stato creato e disponibile
        List<Transaction> transactionList = new ArrayList<>();


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
    }

    private Transaction addSingleTransaction(Account account1){

        if(account1 == null){
            return null;
        }

        Transaction transaction1 = new Transaction();
        transaction1.setDataPresaInCarico(LocalDate.now());
        transaction1.setDataEsecuzione(LocalDate.now().plusDays(-1));
        transaction1.setNumeroOrdine("INTER2019283344056");
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
        transaction1.setDescrizione("Farmacia albergo e ristorante");
        transaction1.setImporto(new BigDecimal(-100.0));
        transaction1.setTRN("0321243443923423423333100T");
        transaction1.setCommissioni(new BigDecimal(00.10));
        transaction1.setTotaleOperazione(new BigDecimal(-100.10));
        transaction1.setAccount(account1);

        return transactionRepository.save(transaction1);
    }
}
