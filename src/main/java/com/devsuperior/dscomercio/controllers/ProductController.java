package com.devsuperior.dscomercio.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscomercio.dto.ProductDTO;
import com.devsuperior.dscomercio.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService service;

/*
 * metodo recebe um id do postman cria um objeto dto chamando o metodo da classe
 * Service com id recebido 
 * a anotação casa o id informado com o long id
 */

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		ProductDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

/*
 * metodo para retornar a lista de produtos paginada através do service
 * (caso de uso -> consultar catalago)
 */
	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
		Page<ProductDTO> dto = service.findAll(pageable);
		return ResponseEntity.ok(dto);
	}

/* metodo insert novo produto 
 * 	aqui vem da requisição do navegador 
 * (caso de uso -> inserir produto)
 * URI- pega o link do recurso criado 
 * @Valid - sempre que receber um ProductDto passo 
 * pelas validações dos campos
 */
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

/* metodo para atualizar um produto ja existente 
 * 	ele ataualiza através do id informado
 * @RequestBody - casa com id informado na requisição	
 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
		 dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}
/* metodo delete sem retorno e sem corpo do obj
 * com codigo 204 
 */
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		 service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
