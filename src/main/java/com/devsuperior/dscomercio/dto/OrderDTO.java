package com.devsuperior.dscomercio.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.devsuperior.dscomercio.entities.Order;
import com.devsuperior.dscomercio.entities.OrderItem;
import com.devsuperior.dscomercio.entities.OrderStatus;

import jakarta.validation.constraints.NotEmpty;

public class OrderDTO {

	private Long id;
	private Instant moment;
	private OrderStatus status;
	
	private ClientDTO client;
	private PaymentDTO payment;
	
	@NotEmpty(message = "Deve conter pelo menos um item")
	private List<OrderItemDTO> items = new ArrayList<>();

	
	public OrderDTO(Long id, Instant moment, OrderStatus status, ClientDTO client, PaymentDTO payment) {
		this.id = id;
		this.moment = moment;
		this.status = status;
		this.client = client;
		this.payment = payment;
	}
	
	public OrderDTO(Order entity) {
		
		BeanUtils.copyProperties(entity, this);
		client = new ClientDTO(entity.getClient());
// se meu pagamento for null e jogo null aqui também
// caso contrario instancio um novo pagamento		
		payment = (entity.getPayment() == null) ? null : new PaymentDTO(entity.getPayment());
/* para cada OrderItemDTO dentro do meu pedido
 * crio um novo OrderItemDTO
 * 
 */		for (OrderItem item : entity.getItems()) {
	 		OrderItemDTO itemDTO = new OrderItemDTO(item);
	 			items.add(itemDTO);
 		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public ClientDTO getClient() {
		return client;
	}

	public void setClient(ClientDTO client) {
		this.client = client;
	}

	public PaymentDTO getPayment() {
		return payment;
	}

	public void setPayment(PaymentDTO payment) {
		this.payment = payment;
	}

	public List<OrderItemDTO> getItems() {
		return items;
	}
/* para cada OrderItemDTO dentro da minha lista items
 * eu faço a soma
 */
	public Double getTotal() {
		double sum = 0.0;
		for (OrderItemDTO item : items) {
			sum += item.getSubTotal();
		}
		return sum;
	}
	
	
}
