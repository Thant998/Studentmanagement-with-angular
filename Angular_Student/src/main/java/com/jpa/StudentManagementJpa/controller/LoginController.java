package com.jpa.StudentManagementJpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jpa.StudentManagementJpa.dao.UserService;
import com.jpa.StudentManagementJpa.dto.User;


@CrossOrigin(origins = "http://localhost:4200")
@RestController

@RequestMapping("/api/v1/")
public class LoginController {
	@Autowired
	private UserService userService ;
	
	@PostMapping(value = "login", produces = "application/json")
	public User login( @RequestBody User user) throws Exception{
		String tempId=user.getId();
		String tempPassword=user.getPassword();
		User userObj=null;
		
		if (tempId !=null && tempPassword !=null) {
			userObj=userService.validateLogin(tempId,tempPassword);
		}
		
		if (userObj==null) {
			throw new Exception("Error Throw you");
		}
		
		return userObj;		
	}
}
