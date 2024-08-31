package com.thesis.pfm.repository.mockBank;

import com.thesis.pfm.model.mockBank.BankCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BankCustomerRepository extends JpaRepository<BankCustomer, String> {
    Optional<BankCustomer> findBySessionId(String sessionId);
    Optional<BankCustomer> findByUsernameAndPassword(String username, String password);
}
