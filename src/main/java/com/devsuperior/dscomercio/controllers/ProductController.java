package com.devsuperior.dscomercio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscomercio.dto.ProductDTO;
import com.devsuperior.dscomercio.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService service;

/* metodo recebe um id do postman cria um objeto dto
 * chamando o metodo da classe Service com id recebido
 */
	
	@GetMapping(value = "/{id}")
	public ProductDTO findById(@PathVariable Long id) {
		ProductDTO dto = service.findById(id);
		return dto;
	}
	
	
}
