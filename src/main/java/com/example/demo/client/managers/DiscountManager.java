package com.example.demo.client.managers;

import java.time.LocalDate;

import com.example.demo.client.ServerContactor;

public class DiscountManager {

	ServerContactor sc;
	String url;
    private static DiscountManager ins = null;

	private DiscountManager(){
		sc = ServerContactor.getInstance();
		url = sc.getUrl()+"/discount";
	}

    public static DiscountManager getInstance() {
        if (ins == null) {
            ins = new DiscountManager();
        }
        return ins;
    }

	public LocalDate getClientDate(String username){
        return sc.getServer().postForObject(url + "/user/localdate", username, LocalDate.class);
    };
	    
	public String[] getDiscountAreas(){
        return sc.getServer().postForObject(url + "/areas", "",String[].class);
    };

	public boolean FirstRide(String userName){
        return sc.getServer().postForObject(url + "/user/firstride", userName, boolean.class);
    };

	public void updatePrice(String driver, int req, double price){
        sc.getServer().postForObject(url + "/update/price/"+req+"/"+price, driver, void.class); // not fisied
    };

	public void CalculateClientPrice(String username){
        sc.getServer().postForObject(url + "/calculate/price", username, void.class);
    };

	public String getClientFromReqID(int requestID){
        return sc.getServer().postForObject(url + "/get/client", requestID, String.class);
    };

	public void checkFirstTime(int requestID){
        sc.getServer().postForObject(url + "/check/firsttime", requestID, void.class);
    };

	
}

/*abstract
    @RequestMapping("/areas")
    public String[] getDiscountAreas(){
        return services.getDiscountAreas();
    };
	
    @RequestMapping("/user/firstride")
	public boolean FirstRide(String username){
        return services.FirstRide(username);
    };

    @RequestMapping("/update/price")
	public void updatePrice(String driver, int req, double price){
        services.updatePrice(driver, req, price);
    };

    @RequestMapping("/calculate/price")
	public void CalculateClientPrice(String username){
        services.CalculateClientPrice(username);
    };

    @RequestMapping("/get/client")
	public String getClientFromReqID(int requestID){
        return services.getClientFromReqID(requestID);
    };

    @RequestMapping("/check/firsttime")
	public void checkFirstTime(int requestID){
        services.checkFirstTime(requestID);
    };*/