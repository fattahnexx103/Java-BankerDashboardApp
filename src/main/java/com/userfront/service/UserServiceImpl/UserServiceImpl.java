package com.userfront.service.UserServiceImpl;

import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.userfront.Dao.RoleDao;
import com.userfront.Dao.UserDao;
import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;
import com.userfront.service.AccountService;
import com.userfront.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private AccountService accountService;
	
	  public User createUser(User user, Set<UserRole> userRoles) {
	        User localUser = userDao.findByUsername(user.getUsername());

	        if (localUser != null) {
	            LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
	        } else {
	            String encryptedPassword = passwordEncoder.encode(user.getPassword());
	            user.setPassword(encryptedPassword);

	            for (UserRole ur : userRoles) {
	                roleDao.save(ur.getRole());
	            }

	            user.getUserRoles().addAll(userRoles);

	            user.setPrimaryAccount(accountService.createPrimaryAccount());
	            user.setSavingsAccount(accountService.createSavingsAccount());

	            localUser = userDao.save(user);
	            
	        }
	        return localUser;
	  }
	
	//save the user
	public void save(User user) {
		userDao.save(user);
	}
	
	//the dao methods
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	//dao methods
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
	//check whether user exists
	public boolean checkUserExists(String username, String email) {
		if(checkUserNameExists(username) || checkEmailExists(username)) {
			return true;
		}else {
			return false;
		}
	}

	//check if email exists
	public boolean checkEmailExists(String email) {
		if(null != findByEmail(email)) {
			return true;
		}
		return false;
	}
	
	//check if email exists
	public boolean checkUserNameExists(String username) {
		if(null != findByUsername(username)) {
			return true;
		}
		return false;
	}
}
