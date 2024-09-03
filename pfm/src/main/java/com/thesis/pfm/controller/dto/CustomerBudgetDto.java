package com.thesis.pfm.controller.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CustomerBudgetDto {
    private int categoryId;  // Cambiato da Long a int
    private BigDecimal amount;
}
