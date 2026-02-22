package com.inventory.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.inventory.dao.ProductDao;
import com.inventory.dao.repository.ProductRepository;
import com.inventory.entity.Product;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class ProductDaoImpl implements ProductDao {
	
	private final ProductRepository repository;

	@Override
	public Product saveProduct(Product product) {
		return repository.save(product);
	}

	@Override
	public Optional<Product> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Product findBySku(String sku) {
		return repository.findBySku(sku);
	}

	@Override
	public List<Product> findAll() {
		return repository.findAll();
	}

	@Override
	public String deleteById(Long id) {
		repository.deleteById(id);
		return "Product Removed Successfully from Inventory";
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Page<Product> search(String query, Pageable pageable) {
		return repository.findBySkuOrName(query, pageable);
	}

}
