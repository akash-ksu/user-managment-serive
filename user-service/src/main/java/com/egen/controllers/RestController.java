package com.egen.controllers;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.egen.entities.Gender;
import com.egen.entities.UserEntity;
import com.egen.exceptions.WebApplicationException;
import com.egen.services.UserService;

@Controller
@RequestMapping("/user")
public class RestController {

	UserService userService;

	static final Logger logger = Logger.getLogger(RestController.class);

	@Autowired
	public RestController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
		if (user.getId() == null || StringUtils.isBlank(user.getId())) {
			user.setId(UUID.randomUUID().toString());
		}
		validateRequestBody(user);
		boolean result = false;
		try {

			result = userService.createUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException("Error while creating user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (result) {
			return new ResponseEntity<UserEntity>(user, HttpStatus.CREATED);
		}
		throw new WebApplicationException("User id already exits", HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<UserEntity> getAllUsers() {
		List<UserEntity> users = null;
		try {
			users = userService.getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException("Error while getting all users", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return users;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateUser(@RequestBody UserEntity user) {
		if (StringUtils.isBlank(user.getId())) {
			throw new WebApplicationException("User id must be provided for update", HttpStatus.BAD_REQUEST);
		}
		validateRequestBody(user);
		boolean result = false;
		try {
			result = userService.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException("Error while updating user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (result) {
			return new ResponseEntity<String>("User updated", HttpStatus.NO_CONTENT);
		}
		throw new WebApplicationException("User does not exist", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(WebApplicationException.class)
	public ResponseEntity<String> handleWebApplicationException(
			@RequestBody WebApplicationException webApplicationException) {
		return new ResponseEntity<String>(webApplicationException.getMessage(), webApplicationException.getStatus());
	}

	public void checkParam(boolean condition, String message) {
		if (condition) {
			throw new WebApplicationException(message, HttpStatus.BAD_REQUEST);
		}
	}

	public void validateRequestBody(UserEntity user) {
		checkParam(StringUtils.isBlank(user.getFirstName()), "First name should be provided");

		checkParam(StringUtils.isBlank(user.getLastName()), "Last name should be provided");

		checkParam(user.getAge() <= 0, "Age must be positive");

		checkParam(
				!(StringUtils.equalsIgnoreCase(user.getGender(), Gender.M.name())
						|| StringUtils.equalsIgnoreCase(user.getGender(), Gender.F.name())),
				"Gender should be either M or F");

		checkParam(!(user.getPhone().length() == 10 && Long.parseLong(user.getPhone()) > 0),
				"phone number should be a 10 digit positive number");
	}
}
