package com.example.demo.services;

import java.util.ArrayList;

import com.example.demo.models.*;
import com.example.demo.database.Storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AdminServices {
    Storage db;
     
    @Autowired
    AdminServices (@Qualifier("sqllite") Storage db){
        this.db = db;
    } 

    /*public User[] getRequestsTo(){
		return db.getAllUsers();
	}*/

    public User[] getAllUsers(){
        ArrayList<User> users = db.getUsers();
        User[] res = new User[users.size()];
        for(int i = 0; i < users.size(); i++)
            res[i] = users.get(i);
        return res;
	}

    public String getEvents(int requestId){
		return db.getEvents(requestId);
	}

    public Driver[] getNonVerifiedDrivers(){
        ArrayList<Driver> drivers = db.getnotVerifiedDriveres();
        Driver[] res = new Driver[drivers.size()];
        for(int i = 0; i < drivers.size(); i++)
            res[i] = drivers.get(i);
        return res;
    }

    public void addOnDiscountAreas(String Area){
        db.addOnDiscountAreas(Area);
    };

	public void DeleteFromDiscountAreas(String Area){
        db.DeleteFromDiscountAreas(Area);
    }

    public void verifyUser(String username) {
        db.verify(username);
    }

    public void setEnable(String username, int state) {
        System.out.println(username + " " + state);
        db.setEnable(username, state);
    }

    public User getUser(String username) {
        return db.getUser(username);
    };
}
