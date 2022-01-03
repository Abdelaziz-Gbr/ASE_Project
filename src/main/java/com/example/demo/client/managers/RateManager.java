package com.example.demo.client.managers;

import java.util.ArrayList;

import com.example.demo.client.ServerContactor;
import com.example.demo.models.*;


public class RateManager {
	ServerContactor sc;
	String url;
	private static RateManager ins = null;
	
	private RateManager() {
		sc = ServerContactor.getInstance();
		url = sc.getUrl() + "/rate";
	}
	
	public static RateManager getInstance() {
		if (ins == null) {
			ins = new RateManager();
		}
		return ins;
	}
	
	public String checkRates(Client client){
		return sc.getServer().postForObject(url + "/check", client, String.class);
	}
	
	public void rateDriver(String dusername, float rate) {
		sc.getServer().postForObject(url + "/driver/"+rate, dusername, void.class);
	}
	
	public ArrayList<Integer> getRates(Driver driver){
		int[] rates = sc.getServer().postForObject(url + "/get", driver, int[].class);
		ArrayList<Integer> res = new ArrayList<Integer>();
		for(int i : rates)
			res.add(i);
		return res;
	}
}
