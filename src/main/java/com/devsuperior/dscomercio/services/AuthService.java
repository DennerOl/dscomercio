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
	
	public void validateSelfOrAdmin(Long userId) {
		
		
// pego o usuario logado		
		User me = userService.authenticated();
		
// testo se user  é admin, se for eu retorno o user
		if (me.hasRole("ROLE_ADMIN")) {
			return;
		}
// testo se o user logado é o mesmo do pedido				
		if (!me.getId().equals( userId)) {
			throw new ForbiddenException("acesso negado, você não é admin para ver os pedidos de outros usuarios");
		}
		
	}
}
