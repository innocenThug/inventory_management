package com.inventory.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.inventory.entity.Product;

public interface ProductDao {
	
	Product saveProduct(Product product);
	Optional<Product> findById(Long id);
	Product findBySku(String sku);
	List<Product> findAll();
	String deleteById(Long id);
	Page<Product> findAll(Pageable pageable);
	Page<Product> search(String query, Pageable pageable);

}
