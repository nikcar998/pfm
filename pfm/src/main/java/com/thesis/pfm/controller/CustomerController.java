package com.thesis.pfm.controller;

import com.thesis.pfm.model.Customer;
import com.thesis.pfm.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer/getAll")
    public List<Customer> getAll() {
        return customerService.getAllStudents();
    }

}
