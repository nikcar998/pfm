package com.thesis.pfm.controller.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CustomerBudgetResponseDto {
    private String customerEmail;
    private int categoryId;
    private BigDecimal amount;
}
