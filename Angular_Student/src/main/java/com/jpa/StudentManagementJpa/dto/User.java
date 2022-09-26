package com.jpa.StudentManagementJpa.dto;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	private String id;	
	private String name;
	private String email;
	private String password;
	private String confirmpassword;
	public String getConpassword() {
		return confirmpassword;
	}
	public void setConpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	private String role;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}