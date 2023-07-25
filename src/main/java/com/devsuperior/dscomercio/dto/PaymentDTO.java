package com.devsuperior.dscomercio.dto;

import java.time.Instant;

import org.springframework.beans.BeanUtils;

import com.devsuperior.dscomercio.entities.Payment;
import com.devsuperior.dscomercio.entities.User;

public class PaymentDTO {

	private Long id;
	private Instant moment;
	
	
	public PaymentDTO(Long id, Instant moment) {
		this.id = id;
		this.moment = moment;
	}
	
	public PaymentDTO(Payment entity) {
		
		 BeanUtils.copyProperties(entity, this);

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
	
	
	
}
