package com.jpa.StudentManagementJpa.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jpa.StudentManagementJpa.dao.UserService;
import com.jpa.StudentManagementJpa.dto.User;
import com.jpa.StudentManagementJpa.model.UserBean;

import net.sf.jasperreports.engine.JRException;


@CrossOrigin(origins = "http://localhost:4200")
@RestController

@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private UserService userService;

    @ModelAttribute("userBean")
    public UserBean getUserBean() {
        return new UserBean();
    }
    
    @GetMapping(value = "/user", produces = "application/json")
    public List<User> displayUser() {
    	return userService.selectAllUser();
    }
    
    @GetMapping(value = "/user/{id}/{name}", produces = "application/json")
    public List<User> searchUser(@PathVariable String id,@PathVariable String name) {
    	List<User> list=userService.searchUser(id,name);
    	return list;
    }

    @PostMapping(value = "/user", produces = "application/json")
    public User addUser(@RequestBody User user) {
    	User dto = new User();
    	dto.setId(userService.userIDGenerator());
    	dto.setName(user.getName());
    	dto.setEmail(user.getEmail());
    	dto.setPassword(user.getPassword());
    	dto.setRole(user.getRole());
    	return userService.save(dto);
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
    	User user = new User();
    	user = userService.getUser(id);
    	return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/user/{id}", produces = "application/json")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User userDetails) {
    	User user = new User();
    	user = userService.getUser(id);
    	user.setId(userDetails.getId());
    	user.setName(userDetails.getName());
    	user.setEmail(userDetails.getEmail());
    	user.setPassword(userDetails.getPassword());
    	user.setRole(userDetails.getRole());
    	User updatedUser = userService.update(user, user.getId());
    	return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable String id) {
    	userService.delete(id);
    	Map<String, Boolean> response = new HashMap<>();
    	response.put("deleted", Boolean.TRUE);
    	return ResponseEntity.ok(response);
    }

    @GetMapping("user/export/html")
	public  void generateHTMLReport() throws JRException, IOException{    
		userService.generateHTMLReport();
	}
	
	 @GetMapping("user/export/pdf")
		public  void generatePDFReport() throws JRException, IOException{    
			userService.generatePDFReport();
		}
	 @GetMapping("user/export/excel")
		public  void generateExcelReport(HttpServletResponse response) throws JRException, IOException{    
			userService.generateExcelReport(response);
		}
}