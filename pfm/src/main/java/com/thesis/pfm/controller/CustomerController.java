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
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/register")
    public String registerMainPage() {
        return "<h1>You need to be registered</h1>";
    }

    @GetMapping("/")
    public String test() {
        return "<h1>Welcome to Money Manager</h1>";
    }

    @GetMapping("/customer/getAll")
    public List<Customer> getAll() {
        return customerService.getAllStudents();
    }

    @GetMapping("/count")
    public String testing(HttpSession session) {
        incrementCount(session, "HOME_VIEW_COUNT");
        return "Home view count: " + session.getAttribute("HOME_VIEW_COUNT");
    }

    private void incrementCount(HttpSession session, String attr) {
        var homeViewCount = session.getAttribute(attr) == null ? 0 : (Integer) session.getAttribute(attr);
        session.setAttribute(attr, homeViewCount + 1);
    }
}
