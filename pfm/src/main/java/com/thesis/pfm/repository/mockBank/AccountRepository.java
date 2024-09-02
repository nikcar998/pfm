package com.thesis.pfm.repository.mockBank;

import com.thesis.pfm.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findByCustomer_Email(String username);
}
