package com.devsuperior.dscomercio.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscomercio.dto.OrderDTO;
import com.devsuperior.dscomercio.dto.OrderItemDTO;
import com.devsuperior.dscomercio.entities.Order;
import com.devsuperior.dscomercio.entities.OrderItem;
import com.devsuperior.dscomercio.entities.OrderStatus;
import com.devsuperior.dscomercio.entities.Product;
import com.devsuperior.dscomercio.entities.User;
import com.devsuperior.dscomercio.repositories.OrderItemRepository;
import com.devsuperior.dscomercio.repositories.OrderRepository;
import com.devsuperior.dscomercio.repositories.ProductRepository;
import com.devsuperior.dscomercio.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserService userService;
	
	
	
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
/* metodo para salvar um novo pedido associado
 * com Product e OrderItem
 */
	@Transactional
	public  OrderDTO insert(OrderDTO dto) {
		
		Order order =  new Order();
		
		order.setMoment(Instant.now());
		order.setStatus(OrderStatus.WAITING_PAYMENT);
	// chamo o nome do usuario logado	
		User user = userService.authenticated();
		order.setClient(user);
/* pego todos os items que veio no meu json
 * 	e instancio no OrderItemDTO	
 */
		for (OrderItemDTO itemDto : dto.getItems()) {
// pego o id do produto e instancio em Product			
			Product product = productRepository.getReferenceById(itemDto.getProductId());
// agora instancio o OrderItem			
			OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
// adiciono os items com seus campos que peguei no pedido		
			order.getItems().add(item);
		}
		
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		return new OrderDTO(order);
	}
	

}
