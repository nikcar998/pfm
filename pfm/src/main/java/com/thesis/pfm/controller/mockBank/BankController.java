package com.thesis.pfm.controller.mockBank;

import com.thesis.pfm.BankCustomerResource;
import com.thesis.pfm.config.JwtTokenProvider;
import com.thesis.pfm.controller.dto.CustomerBudgetDto;
import com.thesis.pfm.controller.dto.MockBankLoginDto;
import com.thesis.pfm.model.Customer;
import com.thesis.pfm.model.CustomerBudget;
import com.thesis.pfm.model.TransactionCategory;
import com.thesis.pfm.service.CustomerService;
import com.thesis.pfm.service.TransactionCategoryService;
import com.thesis.pfm.service.mockBank.BankService;
import com.thesis.pfm.repository.CustomerBudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mockBank")
public class BankController {

    @Autowired
    private BankService bankService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomerService customerservice;

    @Autowired
    private CustomerBudgetRepository customerBudgetRepository;

    @Autowired
    private TransactionCategoryService transactionCategoryService;

    @PostMapping("/bankLogin")
    public ResponseEntity<?> login(@RequestBody MockBankLoginDto dto, @RequestHeader(name="Authorization") String token) {
        Customer customer = customerservice.getCustomerByEmail(tokenProvider.getUserEmailFromJWT(token.substring(7))).get(0);
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

    // Nuovo endpoint per aggiungere un CustomerBudget
    @PostMapping("/addCustomerBudget")
    public ResponseEntity<?> addCustomerBudget(@RequestBody CustomerBudgetDto customerBudgetDto, @RequestHeader(name="Authorization") String token) {
        // Ottieni l'email del cliente dal token JWT
        String customerEmail = tokenProvider.getUserEmailFromJWT(token.substring(7));
        Customer customer = customerservice.getCustomerByEmail(customerEmail).get(0);

        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.FORBIDDEN);
        }

        // Verifica se la categoria esiste utilizzando un ID di tipo int
        TransactionCategory category = transactionCategoryService.getCategoryById(customerBudgetDto.getCategoryId());
        if (category == null) {
            return new ResponseEntity<>("Category not found", HttpStatus.BAD_REQUEST);
        }

        // Crea un nuovo CustomerBudget
        CustomerBudget newBudget = new CustomerBudget();
        newBudget.setCustomerEmail(customerEmail);
        newBudget.setCategoryId(category.getId());
        newBudget.setAmount(customerBudgetDto.getAmount());

        // Salva il CustomerBudget
        customerBudgetRepository.save(newBudget);

        return ResponseEntity.ok("CustomerBudget created successfully");
    }

    @GetMapping("/transactions/month")
    public ResponseEntity<?> getTransactionsByMonth(@RequestHeader String sessionId,
                                                    @RequestHeader String accountCode,
                                                    @RequestParam int year,
                                                    @RequestParam int month,
                                                    @RequestHeader(name="Authorization") String token) {
        Customer customer = customerservice.getCustomerByEmail(tokenProvider.getUserEmailFromJWT(token.substring(7))).get(0);
        if (customer == null) {
            return new ResponseEntity<>("SessionId not valid", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(bankService.getTransactionsByAccountAndMonth(customer, accountCode, year, month));
    }

    // Altri metodi esistenti...
}
