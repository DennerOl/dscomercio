package com.devsuperior.dscomercio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscomercio.dto.OrderDTO;
import com.devsuperior.dscomercio.entities.Order;
import com.devsuperior.dscomercio.repositories.OrderRepository;
import com.devsuperior.dscomercio.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
/* metodo recebe um ID e retorna um produtoDTO
 * vai la no banco busca o produto e converte para 
 * DTO e retorna 
 * orElseThrow- trata uma possivel exceção de id inexistente
 * com uma exceção minha
 * 
 * -versão melhorada usando boas praticas-
 */
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Order order = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new OrderDTO(order);
		
	}
	

}
