package com.mescobar.produto.model;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(value = "product")
public class Product {

	 @Id
	    private String id;
	    private String name;
	    private String description;
	    private BigDecimal price;
	    
}
