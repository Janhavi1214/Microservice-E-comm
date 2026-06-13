package com.day8.ProductService.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.day8.ProductService.entity.Product;
import com.day8.ProductService.exception.InvalidProductException;
import com.day8.ProductService.repo.ProductRepo;

@Service
public class ProductServiceIMPL implements ProductService {

	@Autowired
	private ProductRepo repo;
	
	@Override
	public Product save(Product p) {
		return repo.save(p);
	}

	@Override
	public Product findByCode(int code) throws InvalidProductException {
		return repo.findById(code).orElseThrow(() -> new InvalidProductException("Product not found with code: " + code));
	}

	@Override
	public List<Product> listAll() {
		return repo.findAll();
	}

	@Override
	public List<Product> findByPriceRange(double min, double max) {

	    List<Product> products = repo.findByPriceBetween(min, max);
	    if(products.isEmpty()) {
	        throw new InvalidProductException(
	            "No products found between price " + min + " and " + max
	        );
	    }

	    return products;
	}

}
