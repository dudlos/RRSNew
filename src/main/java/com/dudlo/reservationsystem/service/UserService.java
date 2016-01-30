package com.dudlo.reservationsystem.service;

import java.util.List;

import com.dudlo.reservationsystem.model.RoleEnum;
import com.dudlo.reservationsystem.model.User;

	public interface UserService {

	public User createUser(String firstName, String lastName, String userName, String password,  RoleEnum role);
	
	public User findUserByID(Long userID);
	
	public List<User>getAllUsers();
	
	public long login(String userName, String password);
	
	public User findUserByUserName(String userName);
}
