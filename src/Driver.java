import java.util.ArrayList;
import java.util.Scanner;

public class Driver extends User{
	Scanner scan;
	String licence;
	String nationalId;
	ArrayList<String> fav = SqlDb.getInstance().getFav(this.username);
	ArrayList<Integer> rates;
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

	public void mainmenu(){
		System.out.println("Welcome!");
		System.out.print(SqlDb.getInstance().getFirstName(this.username));
		System.out.println(" " + SqlDb.getInstance().getLastName(this.username));
		while (true){
			scan = new Scanner(System.in);
			System.out.println("1- list Favorites");
			System.out.println("2- list rates");
			System.out.println("3- add Favorites");
			System.out.println("4- list Rides");
            System.out.println("5- list Offers");
			System.out.println("6- log out");
			int ans = Integer.parseInt(scan.nextLine());
			if(ans == 1){
				for(int i =0 ;i < fav.size() ; i++)
					System.out.println(fav.get(i));
			}
			else if (ans == 2) {
				rates = RateManager.getInstance().getRates(this);
				if(rates.isEmpty()) {
					System.out.println("No Rates");
				}
				else {
					for(int rate : rates) {
						System.out.println(rate);
					}
				}
			}
			else if(ans == 3){
				System.out.println("Enter a new Destination");
				String input = scan.nextLine();
				input = input.toLowerCase();
				if(fav.contains(input)){
					System.out.println("Already exists");
				}else {
					fav.add(input);
					SqlDb.getInstance().addFav(this.username, input);
				}

			}
			else if(ans == 4) {
				ListRides();
			}
            else if (ans == 5){
        		ArrayList <Offer>offers=SqlDb.getInstance().checkOffer(username);
                int accipted=-1;
                for(int i=0;i<offers.size();i++)
                {
                    System.out.println(offers.get(i).getOfferInfo());
                    if(offers.get(i).getStatus()==Status.ACCEPTED)
                    {
                        accipted=i;
						break;
                    }
                }
                if(accipted!=-1)
                {
                    System.out.println("ride id "+offers.get(accipted).getRequestId()+" started");
                    System.out.println("press 1 to end it");
                    int ans2 = Integer.parseInt(scan.nextLine());
                    while (ans2!=1) {                                        
                        ans2 = Integer.parseInt(scan.nextLine());
                    }
                    SqlDb.getInstance().setRequestStatus(offers.get(accipted).getRequestId(), Status.ENDED);  
                    SqlDb.getInstance().removeOffer(offers.get(accipted));
                }
                                
			}
			else if (ans == 6){
				break;
			}
			else{
				System.out.println("Invalid input");
				continue;
			}
		}
		//scan.close();
		return;
	}
	
	public void ListRides(){
		ArrayList<Request> requests = new ArrayList<Request>();
		for(String src : fav){
			ArrayList<Request> temp = RequestManager.getInstance().getRequestsTo(src);
			for(Request req : temp)
				requests.add(req);
		}
		if(requests.size() < 1){
			System.out.println("No Available rides right now.");
		}else{
			for(int i =0 ; i <requests.size() ; i++){
				System.out.println(i + "-" + requests.get(i).getInfo());
			}
			System.out.println("Enter Request number: ");
			int num = Integer.parseInt(scan.nextLine());
			System.out.println("Enter offer: ");
			float price = Float.valueOf(scan.nextLine());
			RequestManager.getInstance().makeOffer(new Offer(price, this, requests.get(num)));
		}
		return;
	}

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

	public ArrayList<Integer> getRates() {
		return rates;
	}

	public void setRates(ArrayList<Integer> rates) {
		this.rates = rates;
	}
	
	public String getDriverInfo(){
		return String.format("%-20s%-20s%-20s", firstname , username, avreagerate);
	}

	public Driver(String firstName, String lastName, String username, String password,String licence,String nationalId) {
		super(firstName, lastName, username, password, false);
		this.licence = licence;
		this.nationalId = nationalId;
	}
}