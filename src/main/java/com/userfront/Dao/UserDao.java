package com.userfront.Dao;

import org.springframework.data.repository.CrudRepository;

import com.userfront.domain.User;

public interface UserDao extends CrudRepository<User, Long> {
	
	//the methods
	User findByUsername(String username); //looks up a user by username
	User findByEmail(String email); //looks up a user by email
}
