package com.thesis.pfm.repository;

import com.thesis.pfm.model.Customer;
import com.thesis.pfm.model.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Integer> {
}
