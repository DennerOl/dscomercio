package com.devsuperior.dscomercio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscomercio.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}
