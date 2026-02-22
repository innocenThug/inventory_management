package com.inventory.controller.api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.dto.request.ProductRequestDto;
import com.inventory.dto.request.ProductSearchRequestDto;
import com.inventory.dto.response.PagedResponseDto;
import com.inventory.dto.response.ProductResponseDto;
import com.inventory.dto.response.StockAdjustRequestDto;
import com.inventory.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	private final ProductService service;
	
	public ProductController(ProductService productService) {
        service = productService;
    }
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductResponseDto add(@Valid @RequestBody ProductRequestDto dto) {
		return service.create(dto);	
	}
	
	@GetMapping("/{id:\\d+}")
	public ProductResponseDto find(@PathVariable("id")Long id) {
		return service.getById(id);		
	}
	
    @GetMapping("/by-sku/{sku}")
	public ProductResponseDto findSku(@PathVariable("sku")String sku) {
		return service.getBySku(sku);
	}
	
	@PutMapping("/{id:\\d+}")
	public ProductResponseDto update(@Valid @RequestBody ProductRequestDto dto, @PathVariable("id")Long id) {
		return service.update(id, dto);
	}
	
	@DeleteMapping("/{id:\\d+}")
	public String update(@PathVariable("id")Long id) {
		return service.delete(id);
	}
	
	//Stock Input
    @PostMapping("/{id:\\d+}/stock/in")
	public ProductResponseDto stockIn(@PathVariable("id")Long id, @Valid @RequestBody StockAdjustRequestDto dto) {
		return service.adjustStock(id, dto.getDelta());
	}
	
	//Stock Out (Will throw InsufficientStockExvception if out<stock)
    @PostMapping("/{id:\\d+}/stock/out")
	public ProductResponseDto stockOut(@PathVariable("id")Long id, @Valid @RequestBody StockAdjustRequestDto dto) {
		return service.adjustStock(id, -dto.getDelta());
	}
	
	//Secure search (whiout Page/Pagable exposed)
	@GetMapping("/search")
	public PagedResponseDto<ProductResponseDto> search(
			@RequestParam(value = "query", required = false)String query,
			@RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "size", required = false)Integer size,
			@RequestParam(value = "sortBy", required = false)String sortBy,
			@RequestParam(value = "sortDir", required = false)String sortDir)
	{
		ProductSearchRequestDto request=new ProductSearchRequestDto();
		request.setQuery(query);
		request.setPage(page);
		request.setSize(size);
		request.setSortBy(sortBy);
		request.setSortDir(sortDir);
		return service.search(request);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
