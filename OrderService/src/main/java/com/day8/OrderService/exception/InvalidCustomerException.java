package com.day8.OrderService.exception;

public class InvalidCustomerException extends RuntimeException{

	public InvalidCustomerException() {
		
	}

	public InvalidCustomerException(String message) {
		super(message);
	}

	
}

