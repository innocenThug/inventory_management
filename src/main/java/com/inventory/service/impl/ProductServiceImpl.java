package com.inventory.service.impl;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.inventory.dao.ProductDao;
import com.inventory.dto.ProductMapper;
import com.inventory.dto.request.*;
import com.inventory.dto.response.*;
import com.inventory.entity.Product;
import com.inventory.exception.DuplicateProductException;
import com.inventory.exception.InsufficientStockException;
import com.inventory.exception.ProductNotFoundException;
import com.inventory.service.ProductService;
import com.inventory.service.SortMapping;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{
	
	private final ProductMapper mapper;
	private final ProductDao dao;
	
	//pagination serurity limits
	
	private static final Integer DEFAULT_PAGE=0;
	private static final Integer DEFAULT_SIZE=10;
	private static final Integer MAX_SIZE=100;
	private static final String DEFAULT_SORT_KEY="id";

	@Override
	public ProductResponseDto create(ProductRequestDto dto) {
		Product present= dao.findBySku(dto.getSku());
		if(present!=null)
			throw new DuplicateProductException("SKU : "+present.getSku()+" is already present with ID : "+present.getId());
		return mapper.toDto(dao.saveProduct(mapper.toEntity(dto)));
		
	}

	@Override
	public ProductResponseDto getById(Long id) {
		Optional<Product> found=dao.findById(id);
		if(found.isPresent())
			return mapper.toDto(found.get());
		else {
			throw new ProductNotFoundException("Product not found with ID : "+id+". Enter a valid SKU");
		}
	}

	@Override
	public ProductResponseDto getBySku(String sku) {
		Product product=dao.findBySku(sku);
		if(product==null)
			throw new ProductNotFoundException("Product not found with SKU : "+sku+". Enter a valid SKU");
		return mapper.toDto(product);
	}

	@Override
	public ProductResponseDto update(Long id, ProductRequestDto dto) {
	    Optional<Product> found = dao.findById(id);
	    if (found.isEmpty()) {
	        throw new ProductNotFoundException("Product not found with ID : " + id + ". Enter a valid SKU");
	    }
	    Product existing = found.get();
	    if (dto != null && dto.getSku() != null) {
	        Product conflict = dao.findBySku(dto.getSku());
	        if (conflict != null && conflict.getId() != null && !conflict.getId().equals(id)) {
	            throw new DuplicateProductException("Product already exists with the given SKU : " + conflict.getSku() + " with ID : " + conflict.getId());
	        }
	    }
	    mapper.updateEntity(dto, existing);
	    Product saved = dao.saveProduct(existing);
	    return mapper.toDto(saved);
	}
	
	@Override
	public List<ProductResponseDto> getAll() {
		List<Product> list=dao.findAll();
		if(list==null)
			throw new ProductNotFoundException();
		List<ProductResponseDto> output=new ArrayList<ProductResponseDto>();
		for(Product p : list)
			output.add(mapper.toDto(p));
		return output;
	}

	@Override
	public String delete(Long id) {
		Optional<Product> found=dao.findById(id);
		if(found.isEmpty())
			throw new ProductNotFoundException("Product not found with ID : "+id+". Enter a valid SKU");
		return dao.deleteById(id);
	}

	@Override
	public ProductResponseDto adjustStock(Long id, Integer delta) {
		Product product=mapper.toEntity(getById(id));
		Integer currentQuantity = (product.getQuantity()==null) ? 0 : product.getQuantity();
		Integer updatedQuantity= currentQuantity + delta;
		
		if(updatedQuantity<0)
			throw new InsufficientStockException("Insufficient Stock for ID : "+id+". Requested Quantity : "+updatedQuantity+" Availabe Quantity : "+currentQuantity);
		
		product.setQuantity(updatedQuantity);
		return mapper.toDto(dao.saveProduct(product));
		
	}
	
	@Override
	
    // Secure abstraction: no Page/Pageable leaks outside service
	
	public PagedResponseDto<ProductResponseDto> search(ProductSearchRequestDto req) {
		String query=(req==null) ? null : req.getQuery();
		if(query!=null) query=query.trim();
		if(query!=null && query.length()==0) query=null;
		if(query!=null && query.length()>100) query=query.substring(0, 100);
		
		Integer page=DEFAULT_PAGE;
		Integer size=DEFAULT_SIZE;
		String sortKey=DEFAULT_SORT_KEY;
		String sortDirection="asc";
		if(req!=null) {
			if(req.getPage()!=null && req.getPage()>=0) page=req.getPage();
			if(req.getSize()!=null && req.getSize()>0) size=req.getSize();
			if(req.getSortBy()!=null && req.getSortBy().trim().length()>0) sortKey=req.getSortBy().trim();
			if(req.getSortDir()!=null && req.getSortDir().trim().length()>0) sortDirection=req.getSortDir().trim();
		}
		
		size = (size>MAX_SIZE) ? MAX_SIZE : size;
		
		Map<String, String> allow = SortMapping.PRODUCT_SORT;
		String field= allow.get(sortKey);
		field = (field==null) ? allow.get(DEFAULT_SORT_KEY) : field;
		
		Sort.Direction direction= ("desc".equalsIgnoreCase(sortDirection)) ? Sort.Direction.DESC : Sort.Direction.ASC;
		
		Sort sort = Sort.by(direction,field);
		
		PageRequest pageable = PageRequest.of(page, size, sort);
		
		Page<Product> result= (query==null) ? dao.findAll(pageable) : dao.search(query, pageable);
		
		List<ProductResponseDto> listofResponseDto = new ArrayList<ProductResponseDto>();
		for(Product p : result.getContent())
			listofResponseDto.add(mapper.toDto(p));
		
		return new PagedResponseDto<ProductResponseDto>(listofResponseDto,result.getNumber(),result.getSize(), result.getTotalElements(), result.getTotalPages(), result.isLast());
		
	}

}
