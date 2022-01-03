package com.example.demo.models;

import java.util.Scanner;

import com.example.demo.client.managers.Administration;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("admin")
public class Admin extends User {
	Administration administration = Administration.getInstance();
	Scanner scanner;
	public Admin(String username, String password) {
		super(username, password, false);
	}

	public Admin(){

	}

	public void mainmenu(){
		System.out.println("Welcome!");
		scanner = new Scanner(System.in);
		
		while(true){
			System.out.println("1- list suspended users");
			System.out.println("2- list unsuspended users");
			System.out.println("3- list un-verified drivers");
			System.out.println("4- Verify a driver");
			System.out.println("5- Suspend a user");
			System.out.println("6- Get Events for Request");
			System.out.println("7- Add Area on Discount");
			System.out.println("8- Delete Area on Discount");
			System.out.println("9- log out");
			int ans = Integer.parseInt(scanner.nextLine());
			if(ans == 1){
				User[] users = administration.getAllUsers();
				for(User user : users)
					if(!user.getEnabled())
						System.out.println(user.getUsername());
			}
			else if(ans == 2){
				User[] users = administration.getAllUsers();
				for(User user : users)
					if(user.getEnabled())
						System.out.println(user.getUsername());

			}
			else if(ans == 3){
				Driver[] drivers = administration.getnotVerifiedDriveres();
				if(drivers.length < 1) {
					System.out.println("No Unverified Drivers at the moment");
				}
				else {
					for(Driver driver : drivers){
						System.out.println(driver.getUsername());
					}
				}
			}
			else if(ans == 4){
				System.out.println("Enter User Name");
				String userName = scanner.nextLine();
				administration.verify(userName);
			}
			else if(ans == 5){
				System.out.println("Enter User name of the user to be suspended");
				String userName = scanner.nextLine();
				User user = administration.getUser(userName);
				suspend(user);
			}
			else if(ans == 6) {
				System.out.println("Enter request number");
				int requestId = Integer.parseInt(scanner.nextLine());
				System.out.println(administration.getEvents(requestId));
				
			}
			else if(ans == 7){
				addAreasOnDiscount();
			}
			else if(ans == 8){
				DeleteAreasOnDiscount();
			}
			else if(ans == 9){
				break;
			}
			else{
				System.out.println("Invalid input!");
			}
		}
		return;
	}

	public void suspend(User user) {
		if (!(user instanceof Admin)) {
			user.setEnabled(false);
			administration.setEnable(user.getUsername(), 0);
		}else{
			System.out.println("You can not suspend anther admin!");
		}
		
	}
	
	public void unsuspend(User user){
		if (!(user instanceof Admin)) {
			user.setEnabled(true);
			administration.setEnable(user.getUsername(), 1);
		}else{
			System.out.println("You can not unsuspended anther admin!");
		}

	}
	
	public void accept(Driver driver) {
			driver.setEnabled(true);
			administration.setEnable(driver.getUsername(), 1);
	}

	private void addAreasOnDiscount(){
		System.out.println("Enter Area:");
		String area = scanner.nextLine();
		administration.addOnDiscountAreas(area);
	}

	private void DeleteAreasOnDiscount(){
		System.out.println("Enter Area:");
		String area = scanner.nextLine();
		administration.DeleteFromDiscountAreas(area);
	}
}
