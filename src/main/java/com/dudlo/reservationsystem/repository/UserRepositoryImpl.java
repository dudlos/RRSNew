package com.dudlo.reservationsystem.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dudlo.reservationsystem.model.RoleEnum;
import com.dudlo.reservationsystem.model.User;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public User createUser(String firstName, String lastName, String userName, String password,  RoleEnum role) {
		{
			User newUser = new User(firstName, lastName, userName, password, role);
			manager.persist(newUser);
			return newUser;
		}
	}

	@Override
	public User findUserByID(Long userID) {
		return  manager
				.createQuery(
						"Select c from User c where c.userID = :IDparam",
						User.class)
				.setParameter("IDparam", userID).getSingleResult();
	}

	@Override
	public List<User> getAllUsers() {
		return manager.createQuery("from User", User.class)
		.getResultList();
	}

	@Override
	public long Login(String userName, String password) throws NoResultException{
		User user ;
		user =  manager.createQuery("Select c from User c where c.emailAddress = :userNameParam and c.password = :passwordParam", User.class)
				.setParameter("userNameParam", userName).setParameter("passwordParam", password).getSingleResult();
		return user.getUserID();
	}

	@Override
	public User findUserByUserName(String userName) {
		return manager.createQuery("Select c from User c where c.userName = :userNameParam", User.class)
				.setParameter("userNameParam", userName).getSingleResult();
	}
	
	

}
