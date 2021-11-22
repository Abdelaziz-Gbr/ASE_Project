import java.util.ArrayList;
import java.util.Scanner;

public class Driver extends User{
	Scanner scan;
	String licence;
	String nationalId;
	ArrayList<String> fav;
	ArrayList<Float> rates; 
	float avreagerate;
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
		RequestManager.getInstance().makeOffer(new Offer(price, this));
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

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public ArrayList<String> getFav() {
		return fav;
	}

	public void setFav(ArrayList<String> fav) {
		this.fav = fav;
	}

	public ArrayList<Float> getRates() {
		return rates;
	}

	public void setRates(ArrayList<Float> rates) {
		this.rates = rates;
	}
	
	public String getDriverInfo(){
		return String.format("%-20s%-20s%-20s", firstname , username, avreagerate);
	}
}
