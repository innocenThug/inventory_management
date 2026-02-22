package com.inventory.service;

import java.util.List;

import com.inventory.dto.request.ProductRequestDto;
import com.inventory.dto.request.ProductSearchRequestDto;
import com.inventory.dto.response.PagedResponseDto;
import com.inventory.dto.response.ProductResponseDto;

public interface ProductService {
	
	ProductResponseDto create(ProductRequestDto dto);
	ProductResponseDto getById(Long id);
	ProductResponseDto getBySku(String sku);
    ProductResponseDto update(Long id, ProductRequestDto dto);
	List<ProductResponseDto> getAll();
	String delete(Long id);
	PagedResponseDto<ProductResponseDto> search(ProductSearchRequestDto dto);
	ProductResponseDto adjustStock(Long id, Integer delta);
	
}
