package com.egen.services.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.egen.dao.UserDao;
import com.egen.entities.UserEntity;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
	@Mock
	private UserDao userDao;
	@Mock
	private UserEntity user;

	@Test
	public void testCreateUser() throws Exception {
		when(userDao.createUser(user)).thenReturn(true);
		assertTrue(new UserServiceImpl(userDao).createUser(user));
	}

	@Test
	public void testUpdateUser() throws Exception {
		when(userDao.updateUser(user)).thenReturn(true);
		assertTrue(new UserServiceImpl(userDao).updateUser(user));
	}

	@Test
	public void testGetAllUsers() throws Exception {
		when(userDao.getAllUsers()).thenReturn(Arrays.asList(user));
		assertEquals(Arrays.asList(user), new UserServiceImpl(userDao).getAllUsers());
	}
}
