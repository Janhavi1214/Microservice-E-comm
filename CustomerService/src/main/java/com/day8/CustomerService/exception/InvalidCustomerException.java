package com.day8.CustomerService.exception;

public class InvalidCustomerException extends RuntimeException{

	public InvalidCustomerException() {
		
	}

	public InvalidCustomerException(String message) {
		super(message);
	}

	
}

