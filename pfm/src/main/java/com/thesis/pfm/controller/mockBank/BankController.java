package com.thesis.pfm.controller.mockBank;

import com.thesis.pfm.BankCustomerResource;
import com.thesis.pfm.config.JwtTokenProvider;
import com.thesis.pfm.controller.dto.CustomerBudgetDto;
import com.thesis.pfm.controller.dto.CustomerBudgetResponseDto;
import com.thesis.pfm.controller.dto.MockBankLoginDto;
import com.thesis.pfm.model.Customer;
import com.thesis.pfm.model.CustomerBudget;
import com.thesis.pfm.model.TransactionCategory;
import com.thesis.pfm.service.CustomerBudgetService;
import com.thesis.pfm.service.CustomerService;
import com.thesis.pfm.service.TransactionCategoryService;
import com.thesis.pfm.service.mockBank.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mockBank")
public class BankController {

    @Autowired
    private BankService bankService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerBudgetService customerBudgetService;

    @Autowired
    private TransactionCategoryService transactionCategoryService;

    @PostMapping("/bankLogin")
    public ResponseEntity<?> login(@RequestBody MockBankLoginDto dto, @RequestHeader(name = "Authorization") String token) {
        Customer customer = customerService.getCustomerByEmail(tokenProvider.getUserEmailFromJWT(token.substring(7))).get(0);
        BankCustomerResource bankCustomer = bankService.authenticate(dto.getUsername(), dto.getPassword(), customer);

        if (bankCustomer == null) {
            return new ResponseEntity<>("Bank credentials not valid", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(bankCustomer);
    }

    @GetMapping("/transactionCategories")
    public ResponseEntity<List<TransactionCategory>> getAllTransactionCategories() {
        List<TransactionCategory> categories = transactionCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/addCustomerBudget")
    public ResponseEntity<?> addCustomerBudget(@RequestBody CustomerBudgetDto customerBudgetDto, @RequestHeader(name = "Authorization") String token) {
        String customerEmail = tokenProvider.getUserEmailFromJWT(token.substring(7));

        TransactionCategory category = customerBudgetService.findCategoryById(customerBudgetDto.getCategoryId());
        if (category == null) {
            return new ResponseEntity<>("Category not found", HttpStatus.BAD_REQUEST);
        }

        customerBudgetService.createOrUpdateBudget(customerEmail, customerBudgetDto.getCategoryId(), customerBudgetDto.getAmount());

        return ResponseEntity.ok("CustomerBudget created successfully");
    }

    @DeleteMapping("/deleteCustomerBudget")
    public ResponseEntity<?> deleteCustomerBudget(
            @RequestParam int categoryId,
            @RequestHeader(name = "Authorization") String token) {

        String customerEmail = tokenProvider.getUserEmailFromJWT(token.substring(7));

        try {
            customerBudgetService.deleteBudget(customerEmail, categoryId);
            return ResponseEntity.ok("CustomerBudget deleted successfully");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Budget not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customerBudgets")
    public ResponseEntity<?> getCustomerBudgets(@RequestHeader(name = "Authorization") String token) {
        String customerEmail = tokenProvider.getUserEmailFromJWT(token.substring(7));

        List<CustomerBudget> customerBudgets = customerBudgetService.getCustomerBudgetsByEmail(customerEmail);

        List<CustomerBudgetResponseDto> customerBudgetDtos = customerBudgets.stream()
                .map(budget -> {
                    CustomerBudgetResponseDto dto = new CustomerBudgetResponseDto();
                    dto.setCustomerEmail(budget.getCustomerEmail());
                    dto.setCategoryId(budget.getCategoryId());
                    dto.setAmount(budget.getAmount());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(customerBudgetDtos);
    }

    @GetMapping("/transactions/month")
    public ResponseEntity<?> getTransactionsByMonth(@RequestHeader String sessionId,
                                                    @RequestHeader String accountCode,
                                                    @RequestParam int year,
                                                    @RequestParam int month,
                                                    @RequestHeader(name = "Authorization") String token) {
        Customer customer = customerService.getCustomerByEmail(tokenProvider.getUserEmailFromJWT(token.substring(7))).get(0);
        if (customer == null) {
            return new ResponseEntity<>("SessionId not valid", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(bankService.getTransactionsByAccountAndMonth(customer, accountCode, year, month));
    }
}
