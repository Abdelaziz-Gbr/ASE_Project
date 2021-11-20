import java.util.ArrayList;
import java.util.Scanner;

public class Driver extends User{
	Scanner scan;
	String licence;
	String nationalId;
	ArrayList<String> fav;
	ArrayList<Float> rates; 
	boolean enabled;
	
	public Driver(String username, String password,Boolean enabled) {
		super(username, password, enabled);
	}
	
	public Driver(String username, String password) {
		super(username, password, false);
	}
	
	public Driver(String username, String password,String licence,String nationalId) {
		super(username, password, false);
		this.licence = licence;
		this.nationalId = nationalId;
	}
	
	//public  checkRequests() {
		
	//}
	
	
	public void makeOffer(Offer offer) {
		scan = new Scanner(System.in);
		System.out.println("Enter price for this request");
		float price = Float.parseFloat(scan.nextLine().trim());
	}
	
	public void notifyAccepted(){
		
	}
	
	float avreage;
	public float getAvreage() {
		return avreage;
	}

	public void setAvreage(float avreage) {
		this.avreage = avreage;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	public void rate(float r) {
		if(rates == null)
			rates = new ArrayList<Float>();
		rates.add(r);
	}
	
}
