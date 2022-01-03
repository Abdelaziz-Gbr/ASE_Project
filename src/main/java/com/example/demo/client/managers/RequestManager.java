package com.example.demo.client.managers;

import java.util.ArrayList;

import com.example.demo.models.*;
import com.example.demo.client.ServerContactor;


public class RequestManager {
	private static RequestManager ins = null;
	ServerContactor sc;
	String url;

	private RequestManager() {
		sc = ServerContactor.getInstance();
		url = sc.getUrl() + "/request";

	}
	
	public static RequestManager getInstance() {
		if (ins == null) {
			ins = new RequestManager();
		}
		return ins;
	}

	public ArrayList<Request> getRequestsTo(String place){
		Request[] requests = sc.getServer().postForObject(url + "/get/place", place, Request[].class);
		ArrayList<Request> res = new ArrayList<Request>();
		for(Request r : requests)
			res.add(r); 
		return res;
	}

	public void makeRequest(Request request) {
		sc.getServer().postForObject(url + "/make", request, String.class);
	}
	
	public void makeOffer(Offer offer) {
		offer.setStatus(Status.PENDING);
		sc.getServer().postForObject(url + "/make/offer", offer, String.class);
	}
	
	public void acceptOffer(Offer offer) {
		sc.getServer().postForObject(url + "/accept/offer", offer, void.class);
		offer.setStatus(Status.ACCEPTED);
	}
	
	public void endRequest(Request request) {
		sc.getServer().postForObject(url + "/end", request, void.class);
	}
	
	public ArrayList<Offer> checkOffers(User user){
		Offer[] offers = sc.getServer().postForObject(url + "/check/offers", user, Offer[].class);
		
		ArrayList<Offer> res = new ArrayList<Offer>();
		for(Offer r : offers)
			res.add(r); 
		return res;
	}

	public void setRequestStatus(int requestId,Status s){
		sc.getServer().postForObject( url + "/set/status/"+requestId, s,void.class);
	}

	
	public void removeOffer(Offer offer){
		sc.getServer().postForObject( url + "/remove/offer", offer, void.class);
	}

	public ArrayList<String> getFav(String username){
		ArrayList<String> favs = new ArrayList<String>();
        String[] res = sc.getServer().postForObject(url + "/get/favs", username,String[].class);
        for(int i = 0; i < res.length; i++)
            favs.add(res[i]);
		return favs;
	}
	
	public void addFav(String username,String location){
		sc.getServer().postForObject( url + "/add/fav/"+ location, username, void.class);
	}
}
