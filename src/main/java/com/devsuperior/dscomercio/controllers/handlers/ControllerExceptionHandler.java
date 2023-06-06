package com.devsuperior.dscomercio.controllers.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscomercio.dto.CustomError;
import com.devsuperior.dscomercio.services.exceptions.DatabaseException;
import com.devsuperior.dscomercio.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
	public class ControllerExceptionHandler {
	
/* metodo que vai tratar a exceção ResourceNotFoundException
 * de recurso não encontrado
 * httpServlet- pega a url que deu a exceção para tratar
 * status foi convertido 
 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
	HttpStatus status = HttpStatus.NOT_FOUND;
	// chamo CustomErro passando os atributos obtidos na URL
	CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
	return ResponseEntity.status(status).body(err);
	
	}
	
/* metodo que trata da exceção DatabaseException 
 * de erro de integridade 
 * 	
 */
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<CustomError> database(DatabaseException e, HttpServletRequest request) {
	HttpStatus status = HttpStatus.BAD_REQUEST;
	CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
	return ResponseEntity.status(status).body(err);
	
	}
}

