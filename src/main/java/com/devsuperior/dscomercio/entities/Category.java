package com.devsuperior.dscomercio.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	/*
	 * uma categoria tem uma colecao de produtos criado com set, pois não quero que
	 * repita o id na terceira tabela
	 */
	@ManyToMany(mappedBy = "categories")
	private Set<Product> products = new HashSet<>();

	public Category() {

	}

	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

// get product	
	public Set<Product> getProducts() {
		return products;
	}

}
