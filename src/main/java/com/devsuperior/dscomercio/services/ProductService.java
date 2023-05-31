package com.devsuperior.dscomercio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscomercio.dto.ProductDTO;
import com.devsuperior.dscomercio.entities.Product;
import com.devsuperior.dscomercio.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
/* metodo recebe um ID e retorna um produtoDTO
 * vai la no banco busca o produto e converte para 
 * DTO e retorna
 */
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		// busquei no banco com id de argumento e retorno para result
		Optional<Product> result = repository.findById(id);
		// pego o objeto dentro do optional
		Product product = result.get();
		// converto o product para productDTO
		ProductDTO dto = new ProductDTO(product);
		return dto;
	
	}
}
