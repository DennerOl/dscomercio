package com.devsuperior.dscomercio.controllersIT;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscomercio.dto.OrderDTO;
import com.devsuperior.dscomercio.dto.ProductDTO;
import com.devsuperior.dscomercio.entities.Order;
import com.devsuperior.dscomercio.entities.OrderItem;
import com.devsuperior.dscomercio.entities.OrderStatus;
import com.devsuperior.dscomercio.entities.Product;
import com.devsuperior.dscomercio.entities.User;
import com.devsuperior.dscomercio.tests.ProductFactory;
import com.devsuperior.dscomercio.tests.TokenUtil;
import com.devsuperior.dscomercio.tests.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private ObjectMapper objectMapper;

	private String adminToken, clientToken, invalidToken;
	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private Long existingOrderId, nonExistingOrderId;

	private Order order;
	private OrderDTO orderDTO;
	private User user;

	@BeforeEach
	void setUp() throws Exception {

		clientUsername = "maria@gmail.com";
		clientPassword = "123456";

		adminUsername = "alex@gmail.com";
		adminPassword = "123456";

		existingOrderId = 1L;
		nonExistingOrderId = 1000L;

		adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
		invalidToken = adminToken + "xpto";

		user = UserFactory.createClientUser();
		order = new Order(null, Instant.now(), OrderStatus.WAITING_PAYMENT, user, null);

		Product product = ProductFactory.createProduct();

		OrderItem orderItem = new OrderItem(order, product, 2, 10.0);
		order.getItems().add(orderItem);
	}

	@Test
	public void findByShouldReturnOrderDTOWhenIdExistsAndAdminLogged() throws Exception {

		ResultActions result = mockMvc
				.perform(get("/orders/{id}", existingOrderId)
						.header("Authorization", "Bearer " + adminToken)
						.accept(MediaType.APPLICATION_JSON))
						.andDo(MockMvcResultHandlers.print());
		

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(existingOrderId));
		result.andExpect(jsonPath("$.moment").value("2022-07-25T13:00:00Z"));
		result.andExpect(jsonPath("$.status").value("PAID"));
		result.andExpect(jsonPath("$.client").exists());
		result.andExpect(jsonPath("$.client.name").value("Maria Brown"));
		result.andExpect(jsonPath("$.payment").exists());
		result.andExpect(jsonPath("$.items").exists());
		result.andExpect(jsonPath("$.items[1].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.total").exists());
	}

	@Test
	public void findByShouldReturnOrderDTOWhenIdExistsAndClientLogged() throws Exception {

		ResultActions result = mockMvc
				.perform(get("/orders/{id}", existingOrderId)
						.header("Authorization", "Bearer " + clientToken)
						.accept(MediaType.APPLICATION_JSON));
						
		

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(existingOrderId));
		result.andExpect(jsonPath("$.moment").value("2022-07-25T13:00:00Z"));
		result.andExpect(jsonPath("$.status").value("PAID"));
		result.andExpect(jsonPath("$.client").exists());
		result.andExpect(jsonPath("$.client.name").value("Maria Brown"));
		result.andExpect(jsonPath("$.payment").exists());
		result.andExpect(jsonPath("$.items").exists());
		result.andExpect(jsonPath("$.items[1].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.total").exists());
	}

	@Test
	public void findByShouldReturnForbbiddenWhenIdExistsAndCLientLoggedAndOrderDoesNotBelongUser() throws Exception {

		Long otherOrderId = 2L;
		
		ResultActions result = mockMvc
				.perform(get("/orders/{id}", otherOrderId)
						.header("Authorization", "Bearer " + clientToken)
						.accept(MediaType.APPLICATION_JSON))
						.andDo(MockMvcResultHandlers.print());
		

		result.andExpect(status().isForbidden());
		
	}
	
	@Test
	public void findByShouldReturnNotFoundWhenIdOrderDoesNotExistAndAdminLogged() throws Exception {

		ResultActions result = mockMvc
				.perform(get("/orders/{id}", nonExistingOrderId)
						.header("Authorization", "Bearer " + adminToken)
						.accept(MediaType.APPLICATION_JSON))
						.andDo(MockMvcResultHandlers.print());
		

		result.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void findByShouldReturnNotFoundWhenIdOrderDoesNotExistAndClientLogged() throws Exception {

		ResultActions result = mockMvc
				.perform(get("/orders/{id}", nonExistingOrderId)
						.header("Authorization", "Bearer " + clientToken)
						.accept(MediaType.APPLICATION_JSON))
						.andDo(MockMvcResultHandlers.print());
		

		result.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void findByShouldReturnUnauthorizadWhenIdExistAndInvaldToken() throws Exception {

		ResultActions result = mockMvc
				.perform(get("/orders/{id}", existingOrderId)
						.header("Authorization", "Bearer " + invalidToken)
						.accept(MediaType.APPLICATION_JSON))
						.andDo(MockMvcResultHandlers.print());
		

		result.andExpect(status().isUnauthorized());
		
	}
}
