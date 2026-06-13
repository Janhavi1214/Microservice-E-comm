package com.day8.CustomerService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.day8.CustomerService.entity.Customer;
import com.day8.CustomerService.exception.InvalidCustomerException;
import com.day8.CustomerService.repo.CustomerRepo;


@Service
public class CustomerServiceIMPL implements CustomerService {

    @Autowired
    private CustomerRepo repo;

    @Override
    public Customer save(Customer c) {
        return repo.save(c);
    }

    @Override
    public Customer findById(int cid) throws InvalidCustomerException {
        return repo.findById(cid).orElseThrow(() ->new InvalidCustomerException("Customer not found with id: " + cid));
    }

    @Override
    public List<Customer> listAll() {
        return repo.findAll();
    }

    @Override
    public Customer findByMobile(String mobile)
            throws InvalidCustomerException {

        Customer c = repo.findByMobile(mobile);

        if (c == null) {
            throw new InvalidCustomerException(
                    "Customer not found with mobile number " + mobile);
        }

        return c;
    }
}