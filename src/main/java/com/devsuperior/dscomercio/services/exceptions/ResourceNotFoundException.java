package com.devsuperior.dscomercio.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
// classe para tratar exceções de recursos não encontrado
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
