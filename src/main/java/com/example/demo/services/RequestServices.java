package com.example.demo.services;

import java.util.ArrayList;

import com.example.demo.database.Storage;
import com.example.demo.models.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RequestServices {
    Storage db;
     
    @Autowired
    RequestServices (@Qualifier("sqllite") Storage db){
        this.db = db;
    } 

	public Request[] getRequestsTo(String place){
		ArrayList<Request> requests_arraylist = db.getRequestsTo(place);
        Request[] requests_array = new Request[requests_arraylist.size()]; 
        for(int i = 0; i < requests_arraylist.size();i++){
            requests_array[i] = requests_arraylist.get(i);
        }
		return requests_array;
	}

	public void makeRequest(Request request) {
		db.addRequest(request);
	}
	
	public void makeOffer(Offer offer) {
		db.addOffer(offer);
	}
	
	public void acceptOffer(Offer offer) {
		if(db.acceptOffer(offer)) {
			offer.setStatus(Status.ACCEPTED);
		}
	}
	
	public void endRequest(Request request) {
		db.addRequest(request);
	}
	
	public ArrayList<Offer> checkOffers(User user){
        ArrayList<Offer> offers_arraylist = db.checkOffer(user.getUsername());
		System.out.println("Checking offers for " + user.getUsername() + "  " + offers_arraylist.size());
		return offers_arraylist;
	}

	public void setRequestStatus(int requestId,Status s){
		db.setRequestStatus(requestId, s);
	}

	public void removeOffer(Offer offer){
		db.removeOffer(offer);
	}

	public ArrayList<String> getFav(String username){
		return db.getFav(username);
	}
	
	public void addFav(String username,String location){
		db.addFav(username, location);
	}
}
