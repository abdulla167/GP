package com.GP.testdemo.doctors.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="sign_in")
public class SignIn implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="email")
	private String email;

	@Column(name="user_name")
	private String userName;

	@Column(name="password")
	private String password;

	@Column(name="role")
	private String role;

	@Column(name="enabled")
	private int enabled;

	public SignIn() {

	}

	public SignIn(String email, String userName, String password, String role, int enabled) {
		this.email = email;
		this.userName = userName;
		this.password = "{noop}" + password;
		this.role = role;
		this.enabled = enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = "{noop}" +password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "SignIn [id=" + id + ", email=" + email + ", userName=" + userName + ", password=" + password
				 + "]";
	}
	
	
}
