package com.devsuperior.dscomercio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscomercio.entities.OrderItem;
import com.devsuperior.dscomercio.entities.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {


	
}
