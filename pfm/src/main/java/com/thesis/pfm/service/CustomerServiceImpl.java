package com.thesis.pfm.service;

import com.thesis.pfm.model.Customer;
import com.thesis.pfm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllStudents() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> getCustomerByEmail(String email) { return customerRepository.findByEmail(email);}

}
