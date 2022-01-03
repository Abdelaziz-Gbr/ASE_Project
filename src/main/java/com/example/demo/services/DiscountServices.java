package com.example.demo.services;

import java.time.LocalDate;

import com.example.demo.database.Storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DiscountServices {
    Storage db;
    
    @Autowired
    DiscountServices(@Qualifier("sqllite") Storage db){
        this.db = db;
    }
    
    public LocalDate getClientDate(String username){
        return db.getClientDate(username);
    }

    public String[] getDiscountAreas(){
        return db.getDiscountAreas();
    };
	
	public boolean FirstRide(String username){
        return FirstRide(username);
    };

	public void updatePrice(String driver, int req, double price){
        db.updatePrice(driver, req, price);
    };

	public void CalculateClientPrice(String username){
        db.CalculateClientPrice(username);
    };

	public String getClientFromReqID(int requestID){
        return db.getClientFromReqID(requestID);
    };

	public void checkFirstTime(int requestID){
        db.checkFirstTime(requestID);
    };

	
}
