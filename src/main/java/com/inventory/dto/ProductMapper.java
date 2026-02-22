package com.inventory.dto;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.inventory.dto.request.ProductRequestDto;
import com.inventory.dto.response.ProductResponseDto;
import com.inventory.entity.Product;
import com.inventory.exception.ApiErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
@Component
public class ProductMapper {
		
	public Product toEntity(ProductRequestDto dto) {
		if(dto==null) return null;
		Product product = new Product();
		product.setSku(dto.getSku());
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setQuantity(dto.getQuantity());
		return product;		
	}
	
	public Product toEntity(ProductResponseDto dto) {
		if(dto==null) return null;
		Product product = new Product();
		product.setId(dto.getId());
		product.setSku(dto.getSku());
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setQuantity(dto.getQuantity());
		return product;		
	}
	
	public ProductResponseDto toDto(Product product) {
		if(product==null) return null;
		ProductResponseDto dto = new ProductResponseDto();
		dto.setId(product.getId());
		dto.setSku(product.getSku());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setQuantity(product.getQuantity());
		return dto;	
	}
	
	public void updateEntity(ProductRequestDto dto, Product entity) {
        if (dto == null || entity == null) return;

        entity.setSku(dto.getSku());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());
    }
	
	
	public ResponseEntity<ApiErrorResponse> builder(String message, HttpStatus status, HttpServletRequest req){
		
		ApiErrorResponse response = new ApiErrorResponse(new Date(), status.value(), status.getReasonPhrase(), message, req.getRequestURI());
		
		return new ResponseEntity<ApiErrorResponse>(response, status);
	}

	
	/*
	 * @Mapper(componentModel = "spring") public interface InventoryMapper {
	 * 
	 * InventoryItem toEntity(InventoryItemRequestDTO dto);
	 * 
	 * InventoryItemResponseDTO toDto(InventoryItem entity);
	 * 
	 * void updateEntityFromDto( InventoryItemRequestDTO dto,
	 * 
	 * @MappingTarget InventoryItem entity );
	 */
	
}
