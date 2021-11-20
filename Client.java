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
	}
}
