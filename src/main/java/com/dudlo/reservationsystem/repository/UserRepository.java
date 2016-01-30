package com.dudlo.reservationsystem.repository;

import java.util.List;

import com.dudlo.reservationsystem.model.RoleEnum;
import com.dudlo.reservationsystem.model.User;

public interface UserRepository {
	
	
	public User createUser(String firstName, String lastName, String userName, String password, RoleEnum role);
	
	public User findUserByID(Long userID);
	
	public List<User>getAllUsers();
	
	public long Login( String userName, String password);
	
	public User findUserByUserName(String userName);
}
