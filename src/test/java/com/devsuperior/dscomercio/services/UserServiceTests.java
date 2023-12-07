package com.devsuperior.dscomercio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscomercio.dto.UserDTO;
import com.devsuperior.dscomercio.entities.User;
import com.devsuperior.dscomercio.projections.UserDetailsProjection;
import com.devsuperior.dscomercio.repositories.UserRepository;
import com.devsuperior.dscomercio.tests.UserDetailsFactory;
import com.devsuperior.dscomercio.tests.UserFactory;
import com.devsuperior.dscomercio.util.CustomUserUtil;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

	@InjectMocks
	private UserService service;

	@Mock
	private UserRepository repository;
	
	@Mock
	private CustomUserUtil userUtil;

	private String existingUsername, nonExistingUsername;
	private User user;
	private List<UserDetailsProjection> userDetails;

	@BeforeEach
	void setUp() throws Exception {

		existingUsername = "maria@gmail.com";
		nonExistingUsername = "user@gmail.com";

		user = UserFactory.createCustomClientUser(1l, existingUsername);
		userDetails = UserDetailsFactory.createCustomAdminUser(existingUsername);

		
		Mockito.when(repository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
		Mockito.when(repository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(new ArrayList<>());

// obtendo um user autenticado
		
		Mockito.when(repository.findByEmail(existingUsername)).thenReturn(Optional.of(user));
		Mockito.when(repository.findByEmail(nonExistingUsername)).thenReturn(Optional.empty());


		
		
		
	}
	
	@Test
	public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
		
		UserDetails result = service.loadUserByUsername(existingUsername);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getUsername(), existingUsername);
	}
	
	@Test
	public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
		
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername(nonExistingUsername);
		});
		
	}

	@Test
	public void authenticatedShouldReturnUserWhenUserExists() {
		
		Mockito.when(userUtil.getLoggedUsername()).thenReturn(existingUsername);
		
		User result = service.authenticated();
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getUsername(), existingUsername);
	}
	
	@Test
	public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
		
		Mockito.doThrow(ClassCastException.class).when(userUtil).getLoggedUsername();

		Assertions.assertThrows(UsernameNotFoundException.class, () ->{
			service.authenticated();
		});
	}
	
	// consultar usuario autenticado
	// pegando o metodo que esta dentro da propria classe service
	@Test
	public void getMeShouldReturnUserDTOWhenUserAuthenticated() {
		
		UserService spyUserService = Mockito.spy(service);
		
		Mockito.doReturn(user).when(spyUserService).authenticated();
		
		UserDTO result = spyUserService.getMe();
				
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getEmail(), existingUsername);
			
		
	}
	
	@Test
	public void getMeShouldThrowUsernameNotFoundExceptionWhenUserNotAuthenticated() {
		
		UserService spyUserService = Mockito.spy(service);
		
		Mockito.doThrow(UsernameNotFoundException.class).when(spyUserService).authenticated();
	

		Assertions.assertThrows(UsernameNotFoundException.class, () ->{
			@SuppressWarnings("unused")
			UserDTO result = spyUserService.getMe();
		});
	}
	
	
}
