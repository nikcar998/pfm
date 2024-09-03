package com.thesis.pfm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBudgetId implements Serializable {
    private String customerEmail;
    private int categoryId;
}
