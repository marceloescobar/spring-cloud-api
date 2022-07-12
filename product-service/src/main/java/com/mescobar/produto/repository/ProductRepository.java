package com.mescobar.produto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mescobar.produto.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
