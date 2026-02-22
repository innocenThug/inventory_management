package com.inventory.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PagedResponseDto<T> {
	
	List<T> productList;
	Integer page;
    Integer size;
    Long totalElements;
    Integer totalPages;
    Boolean last;

}
