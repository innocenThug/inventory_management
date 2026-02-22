package com.inventory.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "products")
public class Product {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "Id", nullable = false)
Long id;

@Column(name = "Stock_Keeping_Unit", nullable = false, length = 16, unique = true)
String sku;

@Column(name = "Name", nullable = false, length = 200)
String name;

@Column(name = "Description", length = 500)
String description;

@Column(name = "Price", nullable = false)
BigDecimal price;

@Column(name = "Quantity")
Integer quantity;

}
