package com.example.demo.services;

import com.example.demo.database.Storage;
import com.example.demo.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
    Storage db;
     
    @Autowired
    UserServices (@Qualifier("sqllite") Storage db){
        this.db = db;
    } 

    public User login(User login_user) {
		User user = db.getUser(login_user.getUsername(), login_user.getPassword());
		if(user == null) {
			System.out.println(String.format("A Failed Attemp Happend with username : %s and password %s", login_user.getUsername(),login_user.getPassword()));
		}
		else {
			System.out.println(String.format("user %s has Logged in", user.getUsername()));
		}
		return user;
	}

	public User register(User user){
		if(db.addUser(user)){
			System.out.println(String.format("A new User %s has Logged in", user.getUsername()));
		}
		else{
			System.out.println(String.format("A Failed Register Attemp Happend",user.getUsername()));
			user = null;
		}
		return user;
	}
}
