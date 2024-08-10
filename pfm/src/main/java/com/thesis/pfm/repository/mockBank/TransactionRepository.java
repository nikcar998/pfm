package com.thesis.pfm.repository.mockBank;

import com.thesis.pfm.model.mockBank.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount_Iban(String iban);
}
