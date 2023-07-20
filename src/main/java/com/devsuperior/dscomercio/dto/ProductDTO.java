package com.devsuperior.dscomercio.dto;

import java.util.ArrayList;
import java.util.List;

import com.devsuperior.dscomercio.entities.Category;
import com.devsuperior.dscomercio.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {

	private Long id;
	
	@Size(min = 3, max = 80, message = "Nome precisa ter de 3 a 80 caracteres")
	@NotBlank(message = "Digite um nome válido" )
	private String name;
	
	@Size(min = 3, message = "Descrição precisa ter no mínimo 10 caracteres")
	@NotBlank(message = "Digite um nome válido" )
	private String description;
	
	@Positive(message = "O preço deve ser positivo")
	private Double price;
	private String imgUrl;

	
/* associação com uma lista de categorias 
 * 	
 */
	@NotEmpty(message = "Deve conter pelo menos uma categoria")
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductDTO() {
		
	}

	public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}

	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
		
/* para cada categoria Cat dentro da lista
 * 	eu vou adicionar em categories um categoryDTO
 * ou seja crio um DTO de categorias	
 */
		for (Category cat : entity.getCategories()) {
			categories.add(new CategoryDTO(cat));
		}
		
	}
	
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getPrice() {
		return price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
	
}
