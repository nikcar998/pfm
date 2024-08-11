package com.thesis.pfm.controller.mockBank;

import com.thesis.pfm.config.JwtTokenProvider;
import com.thesis.pfm.controller.dto.MockBankLoginDto;
import com.thesis.pfm.model.Customer;
import com.thesis.pfm.model.mockBank.Account;
import com.thesis.pfm.model.mockBank.Transaction;
import com.thesis.pfm.model.mockBank.BankCustomer;
import com.thesis.pfm.service.CustomUserDetailsService;
import com.thesis.pfm.service.CustomerService;
import com.thesis.pfm.service.mockBank.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mockBank")
public class BankController {

    @Autowired
    private BankService bankService;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private CustomerService customerservice;

    @PostMapping("/bankLogin")
    public ResponseEntity<?> login(@RequestBody MockBankLoginDto dto, @RequestHeader (name="Authorization") String token) {
        Customer cust = customerservice.getCustomerByEmail(tokenProvider.getUserEmailFromJWT(token.substring(7))).get(0);
        BankCustomer bankCustomer = new BankCustomer();
         if(cust.getBankCustomer() == null){
             bankCustomer = bankService.authenticateAndAddBankCustomer(dto.getUsername(), dto.getPassword(), cust);
         }else{
             bankCustomer = bankService.authenticate(dto.getUsername(), dto.getPassword());
         }

         if(bankCustomer == null){
             return new ResponseEntity<String>("Bank credentials not valid", HttpStatus.FORBIDDEN);
         }
         return ResponseEntity.ok(bankCustomer);
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts(@RequestHeader String sessionId) {
        Optional<BankCustomer> customer = isAuthorized(sessionId);
        if(customer.isEmpty()) {
            return new ResponseEntity<String>("SessionId not valid", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(bankService.getAllAccounts(customer));
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> getAllTransactions(@RequestHeader String sessionId, @RequestHeader String accountCode) {
        Optional<BankCustomer> customer = isAuthorized(sessionId);
        if(customer.isEmpty()) {
            return new ResponseEntity<String>("SessionId not valid", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(bankService.getAllTransactions(customer, accountCode));
    }

    private Optional<BankCustomer> isAuthorized(String sessionId){
        return bankService.getCustomerBySessionId(sessionId);
    }

}
