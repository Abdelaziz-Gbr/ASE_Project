package com.example.demo.client.managers;

import java.util.Scanner;

import com.example.demo.client.ServerContactor;
import com.example.demo.models.User;



public class Login {
	Scanner scan;
	ServerContactor sc;
	String url;
	public Login(){
		sc = ServerContactor.getInstance();
		url = sc.getUrl()+"/user/login";
	}

	public User login() {
		scan = new Scanner(System.in);
		System.out.println("enter user name and password");
		String username = scan.nextLine();
		String password = scan.nextLine();
		User login_user = new User(username, password);
		User user = sc.getServer().postForObject(url, login_user, User.class);
		if(user == null) {
			//System.out.println("Login Failed");
		}
		else {
			System.out.println("Logged in successfully ");
		}
		return user;
	}
}
