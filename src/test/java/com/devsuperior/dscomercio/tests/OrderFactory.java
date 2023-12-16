package com.devsuperior.dscomercio.tests;

import java.time.Instant;

import com.devsuperior.dscomercio.entities.Order;
import com.devsuperior.dscomercio.entities.OrderItem;
import com.devsuperior.dscomercio.entities.OrderStatus;
import com.devsuperior.dscomercio.entities.Payment;
import com.devsuperior.dscomercio.entities.Product;
import com.devsuperior.dscomercio.entities.User;

public class OrderFactory {
	
	public static Order createOrder(User client) {
	
	Order order = new Order(1L, Instant.now(), OrderStatus.WAITING_PAYMENT, client, new Payment());

	Product product = ProductFactory.createProduct();
	OrderItem orderItem = new OrderItem(order, product, 2, 10.0);
	order.getItems().add(orderItem);
	
	return order;
	
	
	}
}
