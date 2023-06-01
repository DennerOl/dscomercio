package com.devsuperior.dscomercio.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscomercio.dto.ProductDTO;
import com.devsuperior.dscomercio.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService service;

/*
 * metodo recebe um id do postman cria um objeto dto chamando o metodo da classe
 * Service com id recebido
 */

	@GetMapping(value = "/{id}")
	public ProductDTO findById(@PathVariable Long id) {
		ProductDTO dto = service.findById(id);
		return dto;
	}

/*
 * metodo para retornar a lista de produtos paginada através do service
 */
	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
		Page<ProductDTO> dto = service.findAll(pageable);
		return ResponseEntity.ok(dto);
	}

/* metodo insert novo produto 
 * 	aqui vem da requisição do navegador
 */
	public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
}
