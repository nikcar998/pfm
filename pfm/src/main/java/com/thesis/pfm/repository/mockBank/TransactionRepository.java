package com.thesis.pfm.repository.mockBank;

import com.thesis.pfm.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccount_NumeroContoCorrente(String accountCode);

    @Query("SELECT AVG(monthlySum) FROM (" +
            "SELECT SUM(t.totaleOperazione) as monthlySum " +
            "FROM Transaction t " +
            "WHERE t.account.customer.email = :email " +
            "AND t.dataEsecuzione >= :startDate " +
            "AND t.totaleOperazione < 0 " +
            "GROUP BY YEAR(t.dataEsecuzione), MONTH(t.dataEsecuzione)) as monthlySums")
    BigDecimal findAverageExpanceLastMonths(String email, LocalDate startDate);

    @Query("SELECT AVG(monthlySum) FROM (" +
            "SELECT SUM(t.totaleOperazione) as monthlySum " +
            "FROM Transaction t " +
            "WHERE t.account.customer.email = :email " +
            "AND t.dataEsecuzione >= :startDate " +
            "AND t.totaleOperazione > 0 " +
            "GROUP BY YEAR(t.dataEsecuzione), MONTH(t.dataEsecuzione)) as monthlySums")
    BigDecimal findAverageIncomeLastMonths(String email, LocalDate startDate);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.account.numeroContoCorrente = :accountCode " +
            "AND YEAR(t.dataEsecuzione) = :year " +
            "AND MONTH(t.dataEsecuzione) = :month")
    List<Transaction> findByAccountAndMonth(@Param("accountCode") String accountCode,
                                            @Param("year") int year,
                                            @Param("month") int month);

    ////////////////////// ESEMPIO //////////////////////

//    @Query("SELECT SUM(t.totaleOperazione) FROM Transaction t " +
//            "WHERE t.account.customer.email = :customerEmail " +
//            "AND t.category.id = :categoryId")
//    BigDecimal sumByCustomerAndCategory(@Param("customerEmail") String customerEmail,
//                                        @Param("categoryId") Long categoryId);

    @Query("SELECT t FROM Transaction t JOIN FETCH t.transactionCategory tc WHERE t.account.customer.email = :customerEmail AND tc.id = :categoryId")
    List<Transaction> findTransactionsByCustomerAndCategory(
            @Param("customerEmail") String customerEmail,
            @Param("categoryId") Long categoryId);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.account.customer.email = :email " +
            "AND t.dataEsecuzione BETWEEN :startDate AND :endDate")
    List<Transaction> findByCustomerAndDateRange(@Param("email") String email,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);


}
