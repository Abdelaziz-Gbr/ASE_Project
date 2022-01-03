package com.example.demo.client.managers;

import java.sql.Date;
import java.util.Scanner;

import com.example.demo.models.*;
import com.example.demo.client.ServerContactor;


public class Register {
	Scanner scan;
	ServerContactor sc;
	String url;
	public Register(){
		sc = ServerContactor.getInstance();
		url = sc.getUrl() + "/user/register";
	}

	public User register() {
		scan = new Scanner(System.in);
		User user;
		String firstName;
		String lastName;
		System.out.println("Enter First Name");
		firstName = scan.nextLine();
		System.out.println("Enter Last Name");
		lastName = scan.nextLine();
		System.out.println("Enter Username");
		String username = scan.nextLine();
		System.out.println("Enter Password");
		String password = scan.nextLine();
		System.out.println("Choose the type of your account\n1-Client\n2-Driver");
		int choice = Integer.parseInt(scan.nextLine());

		if(choice == 2) {
			System.out.println("Enter your licence");
			String licences = scan.nextLine();
			System.out.println("Enter nationalId");
			String nationalId = scan.nextLine();

			user = new Driver(firstName , lastName, username ,password, licences, nationalId);
			user.setEnabled(false);
		}
		else {
			user = new Client(firstName , lastName, username,password);
			System.out.println("Enter The day Of your BirthDay");
			int day = Integer.parseInt(scan.nextLine());
			System.out.println("Enter The Month Of your BirthDay");
			int month = Integer.parseInt(scan.nextLine());
			Date date = Date.valueOf("1991-"+ month + "-" + day);// don't mind the year is we don't care about it.
			user = new Client(firstName , lastName, username, password);
			((Client)user).setBirthDay(date);
		}
		user = sc.getServer().postForObject(url, user, User.class);
		if(user == null){
			System.out.println("username already exists");
		}
		return user;
	}
}
