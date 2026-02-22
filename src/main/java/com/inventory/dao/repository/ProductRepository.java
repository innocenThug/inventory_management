package com.inventory.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventory.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Product findBySku(String sku);
	
	@Query("select p from Product p " +
	           "where lower(p.sku) like lower(concat('%', :query, '%')) " +
	           "or lower(p.name) like lower(concat('%', :query, '%'))")
	Page<Product> findBySkuOrName(@Param("query") String query, Pageable pageable);

}
