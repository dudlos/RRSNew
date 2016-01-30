package com.dudlo.reservationsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dudlo.reservationsystem.model.CurrentUser;
import com.dudlo.reservationsystem.model.User;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

	@Autowired
	UserService userService;
	
	@Override
	public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUserByUserName(username);
		return new CurrentUser(user);
	}

}
