package com.egen.dao.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.egen.entities.UserEntity;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {
	@Mock
	private EntityManager entityManager;
	@Mock
	private UserEntity user;
	@Mock
	TypedQuery<UserEntity> typedQuery;

	@Test
	public void testCreateUser_UserExists() throws Exception {
		when(user.getId()).thenReturn("id");
		when(entityManager.find((Class<Object>) isA(Object.class), isA(String.class))).thenReturn("");
		assertFalse(new UserDaoImpl(entityManager).createUser(user));
	}

	@Test
	public void testCreateUser_Success() throws Exception {
		when(user.getId()).thenReturn("id");
		when(entityManager.find((Class<Object>) isA(Object.class), isA(String.class))).thenReturn(null);
		assertTrue(new UserDaoImpl(entityManager).createUser(user));
	}

	@Test
	public void testGetAllUsers() throws Exception {
		when(entityManager.createQuery(isA(String.class), (Class<UserEntity>) isA(Object.class)))
				.thenReturn(typedQuery);
		when(typedQuery.getResultList()).thenReturn(Arrays.asList(user));
		assertEquals(Arrays.asList(user), new UserDaoImpl(entityManager).getAllUsers());
	}

	@Test
	public void testUpdateUser_UserDoesExists() throws Exception {
		when(user.getId()).thenReturn("id");
		when(entityManager.find((Class<Object>) isA(Object.class), isA(String.class))).thenReturn(null);
		assertFalse(new UserDaoImpl(entityManager).updateUser(user));
	}

	@Test
	public void testUpdateUser_Success() throws Exception {
		when(user.getId()).thenReturn("id");
		when(entityManager.find((Class<Object>) isA(Object.class), isA(String.class))).thenReturn("");
		assertTrue(new UserDaoImpl(entityManager).updateUser(user));
	}
}
