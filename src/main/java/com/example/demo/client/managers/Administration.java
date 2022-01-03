package com.example.demo.client.managers;

import com.example.demo.client.ServerContactor;
import com.example.demo.models.Driver;
import com.example.demo.models.User;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public class Administration {
    ServerContactor sc;
	String url;
	private static Administration ins = null;
	
	private Administration() {
		sc = ServerContactor.getInstance();
		url = sc.getUrl() + "/admin";
	}
	
	public static Administration getInstance() {
		if (ins == null) {
			ins = new Administration();
		}
		return ins;
	}

    public void addOnDiscountAreas(String Area){
        sc.getServer().postForObject(url + "/discount/add/areas", Area, ResponseEntity.class);
    };

	public void DeleteFromDiscountAreas(String Area){
        sc.getServer().postForObject(url + "/discount/delete/areas", Area, ResponseEntity.class);
    }

    public User[] getAllUsers(){
        return sc.getServer().getForObject(url + "/get/all/user", User[].class);   
    }

    public Driver[] getnotVerifiedDriveres(){
        return sc.getServer().getForObject(url + "/get/all/nonverified/drivers", Driver[].class);   
    }
    
    public void verify(String username){
        sc.getServer().postForObject(url + "/verify/user/username",username , RequestEntity.class);   
    }

    public User getUser(String username){
        return sc.getServer().postForObject(url + "/get/user/username",username , User.class);   
    }

    public String getEvents(int requestId){
        return sc.getServer().postForObject(url + "/get/events/request",requestId , String.class);   
    }
    
    public void setEnable (String username,int state){
        sc.getServer().postForObject(url + "/enable/user/username/" + String.valueOf(state), username, void.class);
    }

}
