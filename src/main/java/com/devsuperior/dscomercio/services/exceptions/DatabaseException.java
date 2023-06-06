package com.devsuperior.dscomercio.services.exceptions;

public class DatabaseException extends RuntimeException {
// classe para tratar as exceções de banco de dados
	public DatabaseException(String msg) {
		super(msg);
	}
}
