package com.day8.ProductService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.day8.ProductService.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{

	@Query("FROM Product WHERE price Between :min AND :max")
	public List<Product> findByPriceBetween(double min, double max);
	// return list of products that lie between the given price range . spring data JPA knows what does 
	// between mean . if written range instead of it , it won't understand.
	
	//public List<Product> findByName(String name);
}

