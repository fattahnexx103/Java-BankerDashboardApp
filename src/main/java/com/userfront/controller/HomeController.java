package com.userfront.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.Dao.RoleDao;
import com.userfront.Dao.UserDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;
import com.userfront.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleDao roleDao;
	
	//@Controller registers the class as a bean in Spring
	
	@RequestMapping("/")
	public String home() {
		return "redirect:/index"; //redirect to index
	}

	@RequestMapping("/index")
	public String index() {
		return "index"; //goes straight to index if we go to /index
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET) 
	public String signup(Model model) {
		User user = new User(); //bind user to the object
		
		//add the user object to the page to obtain properties
		model.addAttribute("user",user);
		
		return "signup";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signupPost(@ModelAttribute("user") User user, Model model) {
		
		if(userService.checkUserExists(user.getUsername(), user.getEmail())) {
		
		if(userService.checkEmailExists(user.getEmail())) {
			model.addAttribute("emailExists", true);
		}
		
	   if(userService.checkUserNameExists(user.getUsername()) ) {
		   model.addAttribute("usernameExists",true);
	   }
	   
	  return "signup"; 
	 }else {
		 Set<UserRole> userRoles = new HashSet<>();
		 userRoles.add(new UserRole(user,roleDao.findByName("ROLE_USER")));
		 userService.createUser(user,userRoles);	
		 
		 //changed from userService.save(user) to userService.create(user)
		 return "redirect:/";
	 } 
		
   }
	
   @RequestMapping("/userFront")
   public String userFront(Principal principal, Model model) { //principal is the person who logged in
	   User user = userService.findByUsername(principal.getName());
	   PrimaryAccount primaryAccount = user.getPrimaryAccount(); 
	   SavingsAccount savingsAccount = user.getSavingsAccount();
	   
	   model.addAttribute("primaryAccount", primaryAccount);
	   model.addAttribute("savingsAccount", savingsAccount);
	   return "userFront";
   }
	
}
