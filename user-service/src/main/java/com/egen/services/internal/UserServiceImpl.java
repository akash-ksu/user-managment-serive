package com.egen.services.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.egen.dao.UserDao;
import com.egen.entities.UserEntity;
import com.egen.services.UserService;

public class UserServiceImpl implements UserService {

	UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean createUser(UserEntity user) throws Exception {
		return userDao.createUser(user);
	}

	@Override
	public List<UserEntity> getAllUsers() throws Exception {
		return userDao.getAllUsers();
	}

	@Override
	public boolean updateUser(UserEntity user) throws Exception {
		return userDao.updateUser(user);
	}

}
