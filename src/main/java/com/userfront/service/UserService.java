package com.userfront.service;

import java.util.Set;

import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;

public interface UserService {
	
	User findByUsername(String username); //find a user by username
	
	User findByEmail(String email); //find user by email
	
	boolean checkUserExists(String username, String email); //check if user exists

	boolean checkUserNameExists(String username); //check if username exists
	
	boolean checkEmailExists(String email); //check if email exists
	
	void save(User user); //save the user
	
	User createUser(User user, Set<UserRole> userRoles);

}
