package com.thesis.pfm.controller.mockBank;

import com.thesis.pfm.config.JwtTokenProvider;
import com.thesis.pfm.controller.dto.MockBankLoginDto;
import com.thesis.pfm.model.Customer;
import com.thesis.pfm.model.mockBank.BankCustomer;
import com.thesis.pfm.service.CustomerService;
import com.thesis.pfm.service.mockBank.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> login(@RequestBody MockBankLoginDto dto) {
        BankCustomer bankCustomer = bankService.authenticate(dto.getUsername(), dto.getPassword());

        //TODO: Aggiungere codice che aggiunge account e transazioni
         if(bankCustomer == null){
             return new ResponseEntity<String>("Bank credentials not valid", HttpStatus.FORBIDDEN);
         }
         return ResponseEntity.ok(bankCustomer);
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts(@RequestHeader (name="Authorization") String token) {
        Customer customer = customerservice.getCustomerByEmail(tokenProvider.getUserEmailFromJWT(token.substring(7))).get(0);

        if(customer == null) {
            return new ResponseEntity<String>("Error: customer not found", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(bankService.getAllAccounts(customer));
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> getAllTransactions(@RequestHeader String accountCode, @RequestHeader (name="Authorization") String token) {
        Customer customer = customerservice.getCustomerByEmail(tokenProvider.getUserEmailFromJWT(token.substring(7))).get(0);
        if(customer == null) {
            return new ResponseEntity<String>("Error: customer not found", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(bankService.getAllTransactions(customer, accountCode));
    }

    private Optional<BankCustomer> isAuthorized(String sessionId){
        return bankService.getCustomerBySessionId(sessionId);
    }

    @GetMapping("/transactions/month")
    public ResponseEntity<?> getTransactionsByMonth(@RequestHeader String sessionId,
                                                    @RequestHeader String accountCode,
                                                    @RequestParam int year,
                                                    @RequestParam int month,
                                                    @RequestHeader (name="Authorization") String token) {
        Customer customer = customerservice.getCustomerByEmail(tokenProvider.getUserEmailFromJWT(token.substring(7))).get(0);;
        if (customer == null) {
            return new ResponseEntity<>("SessionId not valid", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(bankService.getTransactionsByAccountAndMonth(customer, accountCode, year, month));
    }
}
