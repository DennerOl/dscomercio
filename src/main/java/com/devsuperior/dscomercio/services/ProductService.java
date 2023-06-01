package com.devsuperior.dscomercio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
/*metodo para retornar todos produtos
 * 	paginados através do Page que é uma
 * Stream (caso de uso -> consultar catalago)
 * todos usuarios podem usar
 */
		
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		// busquei no banco 
		Page<Product> result = repository.findAll(pageable);
		return result.map(x -> new ProductDTO(x));
	}
}
