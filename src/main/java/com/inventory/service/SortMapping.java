package com.inventory.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class SortMapping {
	
	public static final Map<String, String> PRODUCT_SORT;
	
	static {
		Map<String, String> map= new HashMap<>();
		map.put("id","id");
		map.put("sku","sku");
		map.put("name","name");
		map.put("quantity","quantity");
		map.put("price","price");
		PRODUCT_SORT=Collections.unmodifiableMap(map);
	
	}
	
	private SortMapping() {}

}
