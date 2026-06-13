package com.day8.CustomerService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.day8.CustomerService.exception.InvalidCustomerException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidCustomerException.class)
	public ResponseEntity<String> handleInvalidCustomer(InvalidCustomerException e){
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
//	@ExceptionHandler(InvalidProductException.class)
//	public ResponseEntity<String> handleInvalidProduct(InvalidProductException e){
//		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
//	}
	
	//@ExceptionHandler(InvalidOrderException.class)
	//public ResponseEntity<String> handleInvalidOrder(InvalidOrderException e){
		//return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	//}
}

