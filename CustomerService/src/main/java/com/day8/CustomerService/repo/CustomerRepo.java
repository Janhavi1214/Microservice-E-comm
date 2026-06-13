package com.day8.CustomerService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.day8.CustomerService.entity.Customer;


public interface CustomerRepo extends JpaRepository<Customer, Integer>{

	
	public Customer findByMobile(String mobile);
	// return the customer with the provided mobile number
}


