package com.thesis.pfm.repository.mockBank;

import com.thesis.pfm.model.mockBank.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findByCustomer_Username(String username);
    Optional<Account> findByIbanAndCustomer_Username(String iban, String username);
}
