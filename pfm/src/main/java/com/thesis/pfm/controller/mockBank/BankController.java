package com.thesis.pfm.controller.mockBank;

import com.thesis.pfm.controller.dto.MockBankLoginDto;
import com.thesis.pfm.model.mockBank.Account;
import com.thesis.pfm.model.mockBank.Transaction;
import com.thesis.pfm.model.mockBank.BankCustomer;
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
@CrossOrigin(origins = "http://localhost:3000")
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping("/bankLogin")
    public ResponseEntity<?> login(@RequestBody MockBankLoginDto dto) {
         BankCustomer customer = bankService.authenticate(dto.getUsername(), dto.getPassword());
         if(customer == null){
             return new ResponseEntity<String>("Bank credentials not valid", HttpStatus.FORBIDDEN);
         }
         return ResponseEntity.ok(customer);
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
    public ResponseEntity<?> getAllTransactions(@RequestHeader String sessionId, @RequestHeader String iban) {
        Optional<BankCustomer> customer = isAuthorized(sessionId);
        if(customer.isEmpty()) {
            return new ResponseEntity<String>("SessionId not valid", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(bankService.getAllTransactions(customer, iban));
    }

    private Optional<BankCustomer> isAuthorized(String sessionId){
        return bankService.getCustomerBySessionId(sessionId);
    }

}
