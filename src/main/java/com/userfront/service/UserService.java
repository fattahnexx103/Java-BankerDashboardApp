package com.userfront.service;

import com.userfront.domain.User;

public interface UserService {
	
	User findByUsername(String username); //find a user by username
	
	boolean checkUserExists(String username, String email); //check if user exists

	boolean checkUserNameExists(String username); //check if username exists
	
	boolean checkEmailExists(String email); //check if email exists
	
	void save(User user); //save the user

}
