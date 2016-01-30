package com.dudlo.reservationsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dudlo.reservationsystem.model.RoleEnum;
import com.dudlo.reservationsystem.model.User;
import com.dudlo.reservationsystem.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User createUser(String firstName, String lastName, String userName, String password,  RoleEnum role) {
	
		return userRepository.createUser(firstName, lastName, userName, password, role);
	}

	@Override
	public User findUserByID(Long userID) {
		return userRepository.findUserByID(userID);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.getAllUsers();
	}

	@Override
	public long login(String userName, String password) {
		return userRepository.Login(userName, password);
	}

	@Override
	public User findUserByUserName(String userName) {
		
		return userRepository.findUserByUserName(userName);
	}

}
