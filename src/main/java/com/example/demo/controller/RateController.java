package com.example.demo.controller;
import com.example.demo.models.*;
import com.example.demo.services.RateServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rate")
@RestController
public class RateController {
    private final RateServices services;

    @Autowired
    public RateController(RateServices services) {
        this.services = services;
    }

    @RequestMapping("/check")
    public String checkRates(@RequestBody Client client){
		return services.checkRates(client);
	}
	
    @RequestMapping("/driver/{rate}")
	public void rateDriver(@RequestBody String driver_username,@PathVariable("rate") float rate) {
		services.rateDriver(driver_username, rate);
	}
	
    @RequestMapping("/get")
	public int[] getRates(@RequestBody Driver driver){
        return services.getRates(driver);
	}
}
