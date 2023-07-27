package com.devsuperior.dscomercio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dscomercio.entities.User;
import com.devsuperior.dscomercio.services.exceptions.ForbiddenException;

@Service
public class AuthService {

	@Autowired
	private UserService userService;
/* passo um id de usuario e testo se o usuario que pediu 
 * 	a requisição se ele é admin ou se ele é o usuario que esta logado
 * caso contrario lanço um 403 pra ele
 */
	
	public void validateSelfOrAdmin(long userId) {
// pego o usuario logado		
		User me = userService.authenticated();
		
// testo se user não é admin e nem o usuario que foi passado no parâmetro
		if (!me.hasRole("ROLE_ADMIN") && me.getId() != userId) {
			throw new ForbiddenException("Acesso negado");
		}
		
	}
}
