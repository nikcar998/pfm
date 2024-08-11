package com.thesis.pfm.repository.mockBank;

import com.thesis.pfm.model.mockBank.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccount_NumeroContoCorrente(String iban);

    @Query("SELECT AVG(monthlySum) FROM (" +
            "SELECT SUM(t.totaleOperazione) as monthlySum " +
            "FROM Transaction t " +
            "WHERE t.account.customer.username = :username " +
            "AND t.dataEsecuzione >= :startDate " +
            "AND t.totaleOperazione < 0 " +
            "GROUP BY YEAR(t.dataEsecuzione), MONTH(t.dataEsecuzione)) as monthlySums")
    BigDecimal findAverageExpanceLastMonths(String username, LocalDate startDate);

    @Query("SELECT AVG(monthlySum) FROM (" +
            "SELECT SUM(t.totaleOperazione) as monthlySum " +
            "FROM Transaction t " +
            "WHERE t.account.customer.username = :username " +
            "AND t.dataEsecuzione >= :startDate " +
            "AND t.totaleOperazione > 0 " +
            "GROUP BY YEAR(t.dataEsecuzione), MONTH(t.dataEsecuzione)) as monthlySums")
    BigDecimal findAverageIncomeLastMonths(String username, LocalDate startDate);

    List<Transaction> findByAccountCustomerUsername(String username);
}
