package com.day8.CustomerService.service;

import java.util.List;
import com.day8.CustomerService.entity.Customer;
import com.day8.CustomerService.exception.InvalidCustomerException;

public interface CustomerService {

	Customer save(Customer c);
	
	Customer findById(int id) throws InvalidCustomerException;
	
	List<Customer> listAll();
	
	Customer findByMobile(String mobile) throws InvalidCustomerException;
}

