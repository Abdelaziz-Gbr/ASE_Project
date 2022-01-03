package com.example.demo.models;

import java.util.ArrayList;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.example.demo.client.managers.*;

@JsonTypeName("driver")
public class Driver extends User{
	Scanner scan;
	String licence;
	String nationalId;
	ArrayList<String> fav;
	ArrayList<Integer> rates;
	float avreagerate;
	boolean enabled;
	
	public Driver(){

	}

	public Driver(String username, String password,Boolean enabled) {
		super(username, password, enabled);
	}
	
	public Driver(String username, String password) {
		super(username, password, false);
	}
	
	public Driver(String username, String password,String licence,String nationalId) {
		super(username, password, false);
		this.licence = licence;
		this.nationalId = nationalId;
	}
	
	//public  checkRequests() {
		
	//}

	public void mainmenu(){
		System.out.println("Welcome!");
		System.out.println(this.firstname + " " + this.lastname);
		fav = RequestManager.getInstance().getFav(this.username);
		while (true){
			scan = new Scanner(System.in);
			ArrayList <Offer>offers = RequestManager.getInstance().checkOffers(this);
			int accepted = -1;
			for(int i=0;i<offers.size();i++)
			{
				//System.out.println(offers.get(i).getOfferInfo());
				if(offers.get(i).getStatus() == Status.ACCEPTED)
				{
					accepted = i;
					break;
				}
			}
			if(accepted != -1)
			{
				System.out.println("ride id "+offers.get(accepted).getRequestId()+" started");
				System.out.println("go to the client and press 1");
				int ans2 = Integer.parseInt(scan.nextLine());
				while (ans2!=1) {                                        
					ans2 = Integer.parseInt(scan.nextLine());
				}
				RequestManager.getInstance().setRequestStatus(offers.get(accepted).getRequestId(), Status.ARRIVED);  
				RequestManager.getInstance().removeOffer(offers.get(accepted));
				System.out.println("take the client to destination and press 1");
				ans2 = Integer.parseInt(scan.nextLine());
				while (ans2!=1) {                                        
					ans2 = Integer.parseInt(scan.nextLine());
				}
				RequestManager.getInstance().setRequestStatus(offers.get(accepted).getRequestId(), Status.ENDED);   
			}
			
			
			System.out.println("1- List Favorites");
			System.out.println("2- List Rates");
			System.out.println("3- Add Favorites");
			System.out.println("4- List Rides");
			System.out.println("5- Refresh");
			System.out.println("6- Log out");

			int ans = Integer.parseInt(scan.nextLine());
			if(ans == 1){
				for(int i =0 ;i < fav.size() ; i++)
					System.out.println(fav.get(i));
			}
			else if (ans == 2) {
				rates = RateManager.getInstance().getRates(this);
				if(rates.isEmpty()) {
					System.out.println("No Rates");
				}
				else {
					for(int rate : rates) {
						System.out.println(rate);
					}
				}
			}
			else if(ans == 3){
				System.out.println("Enter a new Source");
				String input = scan.nextLine().toLowerCase();
				if(fav.contains(input)){
					System.out.println("Already exists");
				}else {
					fav.add(input);
					RequestManager.getInstance().addFav(this.username, input);
				}
			}
			else if(ans == 4) {
				ListRides();
			}
            else if (ans == 5){
        		continue;      
			}
			else if (ans == 6){
				break;
			}
			else{
				System.out.println("Invalid input");
				continue;
			}
		}
		//scan.close();
		return;
	}
	
	public void ListRides(){
		ArrayList<Request> requests = new ArrayList<Request>();
		for(String src : fav){
			ArrayList<Request> temp = RequestManager.getInstance().getRequestsTo(src);
			for(Request req : temp)
				requests.add(req);
		}
		if(requests.size() < 1){
			System.out.println("No Available rides right now.");
		}else{
			for(int i =0 ; i <requests.size() ; i++){
				System.out.println(i + "-" + requests.get(i).getInfo());
			}
			System.out.println("Enter Request number: ");
			int num = Integer.parseInt(scan.nextLine());
			System.out.println("Enter offer: ");
			float price = Float.valueOf(scan.nextLine());
			RequestManager.getInstance().makeOffer(new Offer(price, this, requests.get(num)));
		}
		return;
	}

	public void makeOffer(Offer offer) {
		scan = new Scanner(System.in);
		System.out.println("Enter price for this request");
		float price = Float.parseFloat(scan.nextLine().trim());
		RequestManager.getInstance().makeOffer(new Offer(price, this));
	}
	
	float avreage;
	public float getAvreage() {
		return avreage;
	}

	public void setAvreage(float avreage) {
		this.avreage = avreage;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public ArrayList<String> getFav() {
		return fav;
	}

	public void setFav(ArrayList<String> fav) {
		this.fav = fav;
	}

	public ArrayList<Integer> getRates() {
		return rates;
	}

	public void setRates(ArrayList<Integer> rates) {
		this.rates = rates;
	}
	
	public String getDriverInfo(){
		return String.format("%-20s%-20s%-20s", firstname , username, avreagerate);
	}

	public Driver(String firstName, String lastName, String username, String password,String licence,String nationalId) {
		super(firstName, lastName, username, password, false);
		this.licence = licence;
		this.nationalId = nationalId;
	}
}