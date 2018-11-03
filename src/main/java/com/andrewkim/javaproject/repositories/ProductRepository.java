package com.andrewkim.javaproject.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.andrewkim.javaproject.models.Product;

@Repository
public interface ProductRepository extends CrudRepository <Product, Long> {
	List <Product> findAll();
	List <Product> findByProductNameContaining (String product);
	List <Product> findByProdPriceLessThanEqual (double price);
	List <Product> findByProdDescContaining (String description);
	List <Product> findByProdCategory (String category);
}
