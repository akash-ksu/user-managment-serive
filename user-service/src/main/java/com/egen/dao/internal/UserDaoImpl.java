package com.egen.dao.internal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.egen.dao.UserDao;
import com.egen.entities.UserEntity;

@Transactional
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	EntityManager entityManager;

	public UserDaoImpl() {
	}

	public UserDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public boolean createUser(UserEntity user) throws Exception {
		if (entityManager.find(UserEntity.class, user.getId()) != null) {
			return false;
		}
		entityManager.persist(user);
		return true;
	}

	@Override
	public List<UserEntity> getAllUsers() throws Exception {
		return entityManager.createQuery("SELECT user from UserEntity user", UserEntity.class).getResultList();
	}

	@Override
	public boolean updateUser(UserEntity user) throws Exception {
		if (entityManager.find(UserEntity.class, user.getId()) == null) {
			return false;
		}
		entityManager.merge(user);
		return true;
	}

}
