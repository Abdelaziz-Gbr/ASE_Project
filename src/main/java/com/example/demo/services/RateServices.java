package com.example.demo.services;

import java.util.ArrayList;

import com.example.demo.database.Storage;
import com.example.demo.models.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RateServices {
    Storage db;
     
    @Autowired
    RateServices (@Qualifier("sqllite") Storage db){
        this.db = db;
    } 

    public String checkRates(Client client){
		return db.checkRates(client.getUsername());
	}
	
	public void rateDriver(String driver_username, float rate) {
		db.rateDriver(driver_username, rate);
	}
	
	public int[] getRates(Driver driver){
        ArrayList<Integer> rates_arraylist = db.getRates(driver.getUsername());
        int[] rates_array = new int[rates_arraylist.size()]; 
        for(int i = 0; i < rates_arraylist.size();i++){
            rates_array[i] = rates_arraylist.get(i);
        }
		return rates_array;
	}
}
