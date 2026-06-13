package com.day8.ProductService.service;

import java.util.List;

import com.day8.ProductService.entity.Product;
import com.day8.ProductService.exception.InvalidProductException;

public interface ProductService {

	Product save(Product p);
	
	Product findByCode(int code) throws InvalidProductException;
	
	List<Product> listAll();
	
	List<Product> findByPriceRange(double min, double max);


}

