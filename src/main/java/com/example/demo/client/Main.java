package com.example.demo.client;

import java.util.Scanner;

import com.example.demo.client.managers.*;
import com.example.demo.models.*;

public class Main {

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);

		while (true){
			System.out.println("1- Register");
			System.out.println("2- Log in");
			System.out.println("3- Exit");
			
			int answer = Integer.parseInt(scanner.nextLine());
			
			if(answer == 1){
				Register register = new Register();
				User user =  register.register();
				if(user == null){
					System.out.println("Register Failed");
				}
				else{
					if(user instanceof Driver)
						System.out.println("wait untill verified");
					else{
						user.mainmenu();
					}
				}
			}
			else if (answer == 2){
				Login login = new Login();
				User user = login.login();
				if(user == null){
					System.out.println("Login Failed");
				}
				else{
					user.mainmenu();
				}
			}
			else{
				break;
			}
		}
		
		scanner.close();
		System.exit(0);
	}
}
