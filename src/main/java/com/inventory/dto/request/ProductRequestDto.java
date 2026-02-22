package com.inventory.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequestDto {
	
	@NotNull(message = "Stock Keeping Unit is required")
	@Size(max = 16, message = "SKU must be between 1 and 16 characters")
	String sku;
	
	@NotNull(message = "Name is Required")
	@Size(min=2, max=200, message = "Name must be within 2 and 200 characters")
	String name;
		
	@Size(max = 500, message = "Description max length is 500 characters")
	String description;
	
	@NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
	Integer quantity;
	
	@NotNull(message = "Unit price is required")
	BigDecimal price;
	
}
