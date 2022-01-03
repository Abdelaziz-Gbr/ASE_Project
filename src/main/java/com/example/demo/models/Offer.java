package com.example.demo.models;

public class Offer {
	float price;
	Driver driver;
	int requestId;
	Status status;
	Request req;
	
	public Offer(float price, Driver driver) {
		super();
		this.price = price;
		this.driver = driver;
	}
	public Offer(float price, Driver driver, Request request) {
		super();
		this.req = request;
		this.price = price;
		this.driver = driver;
		this.requestId = request.getRequestID();
	}

	public Offer(){
		status = Status.PENDING;
	}
	
	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public String getOfferInfo() {
		return String.format("%-20s%-15s", String.valueOf(price), status.toString()) + driver.getDriverInfo();
	}
	
}
