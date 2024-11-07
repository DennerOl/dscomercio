package com.devsuperior.dscomercio.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscomercio.dto.UserDTO;
import com.devsuperior.dscomercio.entities.Role;
import com.devsuperior.dscomercio.entities.User;
import com.devsuperior.dscomercio.projections.UserDetailsProjection;
import com.devsuperior.dscomercio.repositories.RoleRepository;
import com.devsuperior.dscomercio.repositories.UserRepository;
import com.devsuperior.dscomercio.services.exceptions.EmailDuplicadoException;
import com.devsuperior.dscomercio.util.CustomUserUtil;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private CustomUserUtil customUserUtil;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
		if (result.size() == 0) {
			throw new UsernameNotFoundException("Email not found");
		}

		User user = new User();
		user.setEmail(result.get(0).getUsername());
		user.setPassword(result.get(0).getPassword());
		for (UserDetailsProjection projection : result) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}

		return user;
	}

	/*
	 * Metodo auxiliar que retorna um usuario
	 * que está logado no sistema
	 */
	protected User authenticated() {
		try {
			String username = customUserUtil.getLoggedUsername();
			return repository.findByEmail(username).get();
		} catch (Exception e) {
			throw new UsernameNotFoundException("Email not found");
		}

	}

	@Transactional(readOnly = true)
	public UserDTO getMe() {
		User user = authenticated();
		return new UserDTO(user);
	}

	public UserDTO criarUser(UserDTO dto) {
		User user = modelMapper.map(dto, User.class);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));

		Role role = roleRepository.findByAuthority("ROLE_CLIENT");

		// Adicionando a role ao usuário
		user.addRole(role); // Usando o método addRole para adicionar a role
		if (repository.findByEmail(user.getEmail()).isPresent()) {
			throw new EmailDuplicadoException("email ja existe na base de dados");
		}
		user = repository.save(user);
		return modelMapper.map(user, UserDTO.class);
	}
}
