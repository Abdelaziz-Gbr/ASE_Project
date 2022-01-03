package com.example.demo.controller;

import com.example.demo.models.Driver;
import com.example.demo.models.User;
import com.example.demo.services.AdminServices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    private final AdminServices services;
    

    @Autowired
    public AdminController(AdminServices services) {
        this.services = services;
    }

    // Tested
    @RequestMapping("/get/all/nonverified/drivers")
    public Driver[] getNonVerifiedDrivers(){
		return services.getNonVerifiedDrivers();
	}

    // Tested
    @RequestMapping("/get/all/user")
    public User[] getAllUsers(){
		return services.getAllUsers();
	}

    @RequestMapping("/get/events/request")
    public String getEvents(@RequestBody int requestId){
		return services.getEvents(requestId);
	}

    @RequestMapping("/discount/add/areas")
	public void addOnDiscountAreas(@RequestBody String Area){
        services.addOnDiscountAreas(Area);
    };

    @RequestMapping("/discount/delete/areas")
	public void DeleteFromDiscountAreas(@RequestBody String Area){
        services.DeleteFromDiscountAreas(Area);
    };

    @RequestMapping("/get/user/username")
	public User getUser(@RequestBody String username){
        return services.getUser(username);
    };

    @RequestMapping("/verify/user/username")
	public void verifyUser(@RequestBody String username){
        services.verifyUser(username);
    };

    @RequestMapping("/enable/user/username/{state}")
	public void setEnable(@RequestBody String username,@PathVariable("state") int state){
        services.setEnable(username, state);
    };

}
