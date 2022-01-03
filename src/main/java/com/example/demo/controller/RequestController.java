package com.example.demo.controller;

import java.util.ArrayList;

import com.example.demo.models.*;
import com.example.demo.services.RequestServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/request")
@RestController
public class RequestController {
    private final RequestServices services;

    @Autowired
    public RequestController(RequestServices services) {
        this.services = services;
    }

    @RequestMapping("/get/place")
    public Request[] getRequestsTo(@RequestBody String place){
		return services.getRequestsTo(place);
	}

    @RequestMapping("/make")
	public void makeRequest(@RequestBody Request request) {
		services.makeRequest(request);
	}
	
    @RequestMapping("/make/offer")
	public void makeOffer(@RequestBody Offer offer) {
		services.makeOffer(offer);
	}

	@RequestMapping("/accept/offer")
	public void acceptOffer(@RequestBody Offer offer) {
		services.acceptOffer(offer);
	}
	@RequestMapping("/end")
	public void endRequest(@RequestBody Request request) {
		services.endRequest(request);
	}
	
    @RequestMapping("/check/offers")
	public Offer[] checkOffers(@RequestBody User user){
        ArrayList<Offer> offers = services.checkOffers(user);
        Offer[] res = new Offer[offers.size()];
        for(int i = 0; i < offers.size();i++)
            res[i] = offers.get(i);
        return res;
	}

    @RequestMapping("/set/status/{id}")
    public void setRequestStatus(@PathVariable("id") int requestId,@RequestBody Status s){
		services.setRequestStatus(requestId, s);
	}

    @RequestMapping("/remove/offer") 
	public void removeOffer(@RequestBody Offer offer){
		services.removeOffer(offer);
	}

    @RequestMapping("/get/favs")
	public String[] getFav(@RequestBody String username){
        ArrayList<String> favs = services.getFav(username);
        String[] res = new String[favs.size()];

        for(int i = 0; i < favs.size(); i++)
            res[i] = favs.get(i);
		
        return res;
	}
	
    @RequestMapping("/add/fav/{location}")
	public void addFav(@RequestBody String username,@PathVariable("location") String location){
		services.addFav(username, location);
	}
}
