package com.inventory.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockAdjustRequestDto {

    @NotNull(message = "Quantity is required (positive for add, negative for remove)")
    Integer delta;
	
}
