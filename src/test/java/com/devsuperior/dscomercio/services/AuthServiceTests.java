package com.devsuperior.dscomercio.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscomercio.entities.User;
import com.devsuperior.dscomercio.services.exceptions.ForbiddenException;
import com.devsuperior.dscomercio.tests.UserFactory;

@ExtendWith(SpringExtension.class)
public class AuthServiceTests {

	@InjectMocks
	private AuthService service;

	@Mock
	private UserService userService;

	private User admin, selfClient, otherClient;

	@BeforeEach
	void setU() throws Exception {
		admin = UserFactory.createAdminUser();
		selfClient = UserFactory.createCustomClientUser(1L, "Bob");
		otherClient = UserFactory.createCustomClientUser(2L, "Ana");

	}

	@Test
	public void validateSelforAdminShouldDoNothingWhenAdminLogged() {

		Mockito.when(userService.authenticated()).thenReturn(admin);

		Long userId = admin.getId();

		Assertions.assertDoesNotThrow(() -> {
			service.validateSelfOrAdmin(userId);
		});
	}

	// user tem acesso ao seu proprio pedido

	@Test
	public void validateSelfOrAdminShouldDoNothingWhenSelfLogged() {

		Mockito.when(userService.authenticated()).thenReturn(selfClient);

		Long userId = selfClient.getId();

		Assertions.assertDoesNotThrow(() -> {
			service.validateSelfOrAdmin(userId);
		});
	}

	@Test
	public void validateSelfOrAdminThrowsForbiddenExceptionWhenClientOtherLogged() {

		Mockito.when(userService.authenticated()).thenReturn(selfClient);

		Long userId = otherClient.getId();
		
		Assertions.assertThrows(ForbiddenException.class, () -> {
			service.validateSelfOrAdmin(userId);
		});

	}

}
