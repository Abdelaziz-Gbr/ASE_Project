import java.util.ArrayList;
import java.util.Scanner;

public class Client extends User {
	Scanner scan;
	public Client(String username, String password) {
		super(username, password, true);
	}
	
	public Client(String username, String password, Boolean enabled) {
		super(username, password, enabled);
	}
	
	public void makeRequest() {
		scan = new Scanner(System.in);
		System.out.println("Enter Source");
		String source = scan.nextLine().trim();
		System.out.println("Enter Destination");
		String destination = scan.nextLine().trim();
		RequestManager.getInstance().makeRequest(new Request(source, destination, this));
	}
	
	@Override
	public void mainmenu() {
		Scanner scan = new Scanner(System.in);
		String choice;
		while(true) {
			String hasRate = RateManager.getInstance().checkRates(this);
			if(hasRate != null && hasRate != "None") {
				System.out.println("Do You want to rate the last Ride?(y/n) ");
				if(scan.nextLine().contains("y")) {
					System.out.println("Enter rate from 1 to 5");
					float rate = Float.parseFloat(scan.nextLine());
					RateManager.getInstance().rateDriver(hasRate, rate);
				}
			}
			System.out.println("1- Make new request\n2- Check for offers\n3- Logout");
			choice = scan.nextLine();
			if(choice.contains("1")) {
				System.out.println("Enter Source and Destination");
				Request request = new Request(scan.nextLine(),scan.nextLine(),this);
				RequestManager.getInstance().makeRequest(request);
			}
			else if(choice.contains("2")) {
				ArrayList<Offer> offers = RequestManager.getInstance().checkOffers(this);
				if(offers.size() == 0) {
					System.out.println("There is no Offers Right Now");
				}
				else {
					System.out.println("Choose an Offer");
					System.out.println(String.format("%-20s%-15s%-20s%-20s%-20s", "Price","State","First Name","Driver Username","Average Rate"));
					for(int i=0; i<offers.size();i++ ) { 
						System.out.println(i+" "+offers.get(i).getOfferInfo());
					}
                    int choise=Integer.parseInt(scan.nextLine());
                    RequestManager.getInstance().acceptOffer(offers.get(choise));
				}
				
			}
			else if(choice.contains("3")){
				break;
			}
			
		}
	}
}
