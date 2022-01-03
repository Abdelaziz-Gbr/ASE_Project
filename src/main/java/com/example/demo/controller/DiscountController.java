package com.example.demo.controller;

import java.time.LocalDate;

import com.example.demo.services.DiscountServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/discount")
@RestController
public class DiscountController {
    private final DiscountServices services;

    @Autowired
    public DiscountController(DiscountServices services) {
        this.services = services;
    }

    @RequestMapping("/user/localdate")
    public LocalDate getClientDate(@RequestBody String username){
        return services.getClientDate(username);
    }

    @RequestMapping("/areas")
    public String[] getDiscountAreas(){
        return services.getDiscountAreas();
    };
	
    @RequestMapping("/user/firstride")
	public boolean FirstRide(@RequestBody String username){
        return services.FirstRide(username);
    };

    @RequestMapping("/update/price/{req}/{price}")
	public void updatePrice(@RequestBody String driver, @PathVariable("req") int req, @PathVariable("price") double price){
        services.updatePrice(driver, req, price);
    };

    @RequestMapping("/calculate/price")
	public void CalculateClientPrice(@RequestBody String username){
        services.CalculateClientPrice(username);
    };

    @RequestMapping("/get/client")
	public String getClientFromReqID(@RequestBody int requestID){
        return services.getClientFromReqID(requestID);
    };

    @RequestMapping("/check/firsttime")
	public void checkFirstTime(@RequestBody int requestID){
        services.checkFirstTime(requestID);
    };
}
