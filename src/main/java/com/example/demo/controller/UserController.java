package com.example.demo.controller;

import com.example.demo.models.User;
import com.example.demo.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserServices services;

    @Autowired
    public UserController(UserServices services) {
        this.services = services;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public User login(@RequestBody User user){
        //System.out.println(user.getUsername() + " " + user.getPassword() + " " + user.getClass().getName());
        user = services.login(user);
        //System.out.println(user.getUsername() + " " + user.getPassword() + " " + user.getClass().getName());
        return user;
    }
    
    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public User register(@RequestBody User user){
        return services.register(user);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/test")
    public User test(@RequestBody User user){
        System.out.println(user.getUsername());
        return user;
    }
}
