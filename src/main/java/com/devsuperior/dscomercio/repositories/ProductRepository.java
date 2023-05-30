package com.devsuperior.dscomercio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscomercio.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
