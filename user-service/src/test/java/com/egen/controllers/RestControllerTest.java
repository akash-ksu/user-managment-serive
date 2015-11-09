package com.egen.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.egen.entities.UserEntity;
import com.egen.exceptions.WebApplicationException;
import com.egen.services.UserService;

@RunWith(MockitoJUnitRunner.class)
public class RestControllerTest {
	@Mock
	private UserService service;
	private UserEntity user;
	private RestController restController;

	@Before
	public void setup() {
		user = new UserEntity("1234456", "abc", "xy", "asd", 25, "M", "1234567890", "66213");
		restController = new RestController(service);
	}

	@Test
	public void testCreateUser_userCreated() throws Exception {
		when(service.createUser(user)).thenReturn(true);
		ResponseEntity<UserEntity> response = restController.createUser(user);
		assertEquals(user, response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test(expected = WebApplicationException.class)
	public void testCreateUser_exceptionGenerated() throws Exception {
		when(service.createUser(user)).thenThrow(Exception.class);
		restController.createUser(user);
	}

	@Test(expected = WebApplicationException.class)
	public void testCreateUser_userNotCreated() throws Exception {
		when(service.createUser(user)).thenReturn(false);
		restController.createUser(user);
	}

	@Test
	public void testGetAllUsers_success() throws Exception {
		UserEntity user1 = new UserEntity("1234457", "abc1", "xy2", "asd", 25, "F", "1234567890", "66213");
		when(service.getAllUsers()).thenReturn(Arrays.asList(user, user1));
		assertEquals(Arrays.asList(user, user1), restController.getAllUsers());
	}

	@Test(expected = WebApplicationException.class)
	public void testGetAllUsers_exceptionGenerated() throws Exception {
		when(service.getAllUsers()).thenThrow(Exception.class);
		restController.getAllUsers();
	}

	@Test(expected = WebApplicationException.class)
	public void testUpdateUsers_BlankUserId() {
		user.setId(StringUtils.EMPTY);
		restController.updateUser(user);
	}

	@Test(expected = WebApplicationException.class)
	public void testUpdateUsers_exceptionGenerated() throws Exception {
		when(service.updateUser(user)).thenThrow(Exception.class);
		restController.updateUser(user);
	}

	@Test
	public void testUpdateUsers_userUpdated() throws Exception {
		when(service.updateUser(user)).thenReturn(true);
		ResponseEntity<String> response = restController.updateUser(user);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test(expected = WebApplicationException.class)
	public void testUpdateUsers_userNotUpdated() throws Exception {
		when(service.updateUser(user)).thenReturn(false);
		restController.updateUser(user);
	}

	@Test
	public void testHandleWebApplicationException() {
		ResponseEntity<String> response = restController
				.handleWebApplicationException(new WebApplicationException("test", HttpStatus.OK));
		assertEquals("test", response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test(expected = WebApplicationException.class)
	public void testCheckParam_true() {
		restController.checkParam(true, "some message");
	}

	@Test(expected = WebApplicationException.class)
	public void testValidateRequestBody_emptyFirstName() {
		user.setFirstName(StringUtils.EMPTY);
		restController.validateRequestBody(user);
	}

	@Test(expected = WebApplicationException.class)
	public void testValidateRequestBody_emptyLastName() {
		user.setLastName(StringUtils.EMPTY);
		restController.validateRequestBody(user);
	}

	@Test(expected = WebApplicationException.class)
	public void testValidateRequestBody_negativeAge() {
		user.setAge(-1);
		restController.validateRequestBody(user);
	}

	@Test(expected = WebApplicationException.class)
	public void testValidateRequestBody_invalidGender() {
		user.setGender("invalid");
		restController.validateRequestBody(user);
	}

	@Test(expected = WebApplicationException.class)
	public void testValidateRequestBody_invalidPhoneLength() {
		user.setPhone("1");
		restController.validateRequestBody(user);
	}

	@Test(expected = WebApplicationException.class)
	public void testValidateRequestBody_negativePhone() {
		user.setPhone("-123456789");
		restController.validateRequestBody(user);
	}
}