package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

	@JsonTypeInfo(
      use = JsonTypeInfo.Id.NAME,  
      property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = "admin"),
        @JsonSubTypes.Type(value = Driver.class, name = "driver"),
		@JsonSubTypes.Type(value = Client.class, name = "client")
    })
public class User {
	String username, password, firstname, lastname;
	Boolean enabled;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public User(){
		
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(String username, String password,Boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public User(String firstname, String lastname, String username, String password,Boolean enabled) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}
	
	public void mainmenu() {
		
	}
}
