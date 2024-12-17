package com.devsuperior.dscomercio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscomercio.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

  void deleteByclient_id(Long client_id);

}
