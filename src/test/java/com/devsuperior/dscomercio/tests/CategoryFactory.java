package com.devsuperior.dscomercio.tests;

import com.devsuperior.dscomercio.entities.Category;

public class CategoryFactory {

	public static Category createCategory() {
		return new Category(1L, "Games");
	}
	
	public static Category createCategory2(Long id, String name) {
		return new Category(id, name);
	}
}
