package com.devsuperior.dscomercio.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.devsuperior.dscomercio.dto.UserDTO;
import com.devsuperior.dscomercio.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService service;

	/*
	 * Metodo para retornar um usuario logado
	 * no sistema
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
	@GetMapping(value = "/me")
	public ResponseEntity<UserDTO> getMe() {
		UserDTO dto = service.getMe();
		return ResponseEntity.ok(dto);
	}

	@PostMapping()
	public ResponseEntity<UserDTO> novoUser(@RequestBody UserDTO dto, UriComponentsBuilder uriBuilder) {
		UserDTO userCriado = service.criarUser(dto);

		URI endereco = uriBuilder.path("/users/{id}").buildAndExpand(userCriado.getId()).toUri();

		return ResponseEntity.created(endereco).body(userCriado);

	}

	@PatchMapping(value = "/perfil")
	public ResponseEntity<UserDTO> update(@RequestBody UserDTO dto) {
		dto = service.update(dto);
		return ResponseEntity.ok(dto);
	}

}
