package com.devsuperior.dscomercio.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscomercio.dto.OrderDTO;
import com.devsuperior.dscomercio.entities.Order;
import com.devsuperior.dscomercio.entities.User;
import com.devsuperior.dscomercio.repositories.OrderRepository;
import com.devsuperior.dscomercio.services.exceptions.ForbiddenException;
import com.devsuperior.dscomercio.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscomercio.tests.OrderFactory;
import com.devsuperior.dscomercio.tests.UserFactory;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {

	
	@InjectMocks
	private OrderService service;
	
	@Mock
	private OrderRepository repository;
	
	@Mock
	private AuthService authService;
	
	
	private Long existingOrderId, nonExistingOrderId;
	private Order order;
	private OrderDTO orderDTO;
	private User admin, client;
	
	@BeforeEach
	void setu() throws Exception{
	existingOrderId = 1L;
	nonExistingOrderId = 2L;
	
	admin = UserFactory.createCustomAdminUser(1L,"Jef");
	client = UserFactory.createCustomClientUser(2L, "Bob");
	
	order = OrderFactory.createOrder(client);
	
	orderDTO = new OrderDTO(order);

	
	Mockito.when(repository.findById(existingOrderId)).thenReturn(Optional.of(order));	
	Mockito.when(repository.findById(nonExistingOrderId)).thenReturn(Optional.empty());	

	
	
	
	}
	
	@Test
	public void findByIdShouldReturnOrderDTOWhenIdExistsAndAdminLogged() {
		
		Mockito.doNothing().when(authService).validateSelfOrAdmin(any());
		
		OrderDTO result = service.findById(existingOrderId);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingOrderId);
	}
	
	@Test
	public void findByIdShouldReturnOrderDTOWhenIdExistsAndSelfClientLogged() {
		
		Mockito.doNothing().when(authService).validateSelfOrAdmin(any());
		
		OrderDTO result = service.findById(existingOrderId);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingOrderId);
	}	
	
	
	@Test
	public void findByIdShouldThrowsForbiddenExceptionWhenIdExistsAndOtherClientLogged() {
		
		Mockito.doThrow(ForbiddenException.class).when(authService).validateSelfOrAdmin(any());
		
		Assertions.assertThrows(ForbiddenException.class,() -> {
			OrderDTO result = service.findById(existingOrderId);
		});
	}
	
	

	@Test
	public void findByIdShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Mockito.doNothing().when(authService).validateSelfOrAdmin(any());
		
		Assertions.assertThrows(ResourceNotFoundException.class,() -> {
			OrderDTO result = service.findById(nonExistingOrderId);
		});
	}
	
}
