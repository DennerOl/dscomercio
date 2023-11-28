package com.devsuperior.dscomercio.tests;

import com.devsuperior.dscomercio.entities.Category;
import com.devsuperior.dscomercio.entities.Product;

public class ProductFactory {

	public static Product createProduct() {
// produto tem uma categoria 		
	Category category = CategoryFactory.createCategory();
	Product product = new Product(1L, "Controle PS5", "Descrição", 399.00, "URLProduto");
	
	product.getCategories().add(category);
	return product;
	
	}
	
	public static Product createProduct2(String name) {
		Product product = createProduct();
		product.setName(name);
		return product;
	}
}
