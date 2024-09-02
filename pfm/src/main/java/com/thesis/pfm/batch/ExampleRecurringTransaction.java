//package com.thesis.pfm.batch;
//
//import com.thesis.pfm.model.Customer;
//import com.thesis.pfm.model.Transaction;
//import com.thesis.pfm.repository.CustomerRepository;
//import com.thesis.pfm.repository.mockBank.TransactionRepository;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
//import org.springframework.boot.autoconfigure.batch.BatchProperties;
//import org.springframework.context.annotation.Bean;
//import jakarta.persistence.EntityManagerFactory;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//
//public class ExampleRecurringTransaction {
//
//    private class Subscription {}
//    private EntityManagerFactory entityManagerFactory;
//
//    @Bean
//    public Job recurringTransactionJob(JobBuilderFactory jobBuilderFactory,
//                                       Step recurringTransactionStep) {
//        return jobBuilderFactory.get("recurringTransactionJob")
//                .start(recurringTransactionStep)
//                .build();
//    }
//
//    @Bean
//    public Step recurringTransactionStep(StepBuilderFactory stepBuilderFactory,
//                                         ItemReader<Customer> customerReader,
//                                         ItemProcessor<Customer, Subscription> recurringTransactionProcessor,
//                                         ItemWriter<Subscription> subscriptionWriter) {
//        return stepBuilderFactory.get("recurringTransactionStep")
//                .<Customer, Subscription>chunk(10)
//                .reader(customerReader)
//                .processor(recurringTransactionProcessor)
//                .writer(subscriptionWriter)
//                .build();
//    }
//
//    @Bean
//    public ItemReader<Customer> customerReader(CustomerRepository customerRepository) {
//        int totalGroups = 7; // Numero totale di gruppi
//        int currentGroup = (LocalDate.now().getDayOfYear() % totalGroups); // Gruppo corrente basato sul giorno dell'anno
//
//        return new JpaPagingItemReaderBuilder<Customer>()
//                .name("customerReader")
//                .queryString("SELECT c" +
//                        "FROM Customer c" +
//                        "WHERE MOD(TO_NUMBER(SUBSTR(DBMS_CRYPTO.HASH(UTL_RAW.CAST_TO_RAW(c.email), 2), 1, 8), 'XXXXXXXX'), :totalGroups) = :currentGroup")
//                .entityManagerFactory(entityManagerFactory)
//                .parameterValues(Map.of("totalGroups", totalGroups, "currentGroup", currentGroup))
//                .pageSize(10)
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<Customer, List<Subscription>> recurringTransactionProcessor(TransactionRepository transactionRepository) {
//        return customer -> {
//            List<Transaction> transactions = transactionRepository.findByCustomerAndDateRange(customer.getEmail(), LocalDate.now().minusMonths(9), LocalDate.now());
//            Map<String, List<Transaction>> groupedTransactions = groupTransactionsByCategoryAndKey(transactions);
//
//            List<Subscription> recurringSubscriptions = new ArrayList<>();
//            for (Map.Entry<String, List<Transaction>> entry : groupedTransactions.entrySet()) {
//                List<Transaction> matchingTransactions = findRecurringTransactions(entry.getValue());
//                if (!matchingTransactions.isEmpty()) {
//                    Subscription subscription = createSubscriptionFromTransactions(matchingTransactions);
//                    recurringSubscriptions.add(subscription);
//                }
//            }
//            return recurringSubscriptions.isEmpty() ? null : recurringSubscriptions;
//        };
//    }
//
//    @Bean
//    public ItemWriter<Subscription> subscriptionWriter(SubscriptionRepository subscriptionRepository) {
//        return subscriptions -> subscriptionRepository.saveAll(subscriptions);
//    }
//
//    // Utility methods for processing
//    private Map<String, List<Transaction>> groupTransactionsByCategoryAndKey(List<Transaction> transactions) {
//        return transactions.stream().collect(Collectors.groupingBy(transaction ->
//                transaction.getCategory().getId() + "-" + transaction.getType() + "-" + transaction.getBeneficiaryOrPayer() + "-" + transaction.getAmount() + "-" + transaction.getDescription()));
//    }
//
//    private List<Transaction> findRecurringTransactions(List<Transaction> transactions) {
//        transactions.sort(Comparator.comparing(Transaction::getExecutionDate));
//        List<Transaction> recurringTransactions = new ArrayList<>();
//        for (int i = 1; i < transactions.size(); i++) {
//            Period period = Period.between(transactions.get(i - 1).getExecutionDate(), transactions.get(i).getExecutionDate());
//            if ((period.getMonths() == 1 || period.getMonths() == 3) && period.getDays() == 0) {
//                recurringTransactions.add(transactions.get(i - 1));
//                recurringTransactions.add(transactions.get(i));
//            }
//        }
//        return recurringTransactions;
//    }
//
//    private Subscription createSubscriptionFromTransactions(List<Transaction> transactions) {
//        Transaction firstTransaction = transactions.get(0);
//        return new Subscription(firstTransaction.getCustomer().getEmail(),
//                firstTransaction.getCategory(),
//                firstTransaction.getAmount(),
//                transactions.size() >= 3 ? "quarterly" : "monthly");
//    }
//}
//
