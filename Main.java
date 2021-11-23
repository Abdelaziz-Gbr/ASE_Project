import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	
	public static void main(String args[]) {
		//SqlDb.selectAll();
		
		//SqlDb.close();
		/*
		System.out.println("enter username\n"
				+ "client1\n"
				+ "enter password\n"
				+ "123456789\n"
				+ "welcome client1\n"
				+ "make ride\n"
				+ "enter source\n"
				+ "giza\n"
				+ "enter distnation\n"
				+ "cairo\n"
				+ "searching for drivers...\n"
				+ "driver1 offering 100 egp for your ride\n"
				+ "accept\n"
				+ "notifing driver\n");
		
		System.out.println("enter username\n"
				+ "driver1\n"
				+ "enter password\n"
				+ "123456789\n"
				+ "welcome driver1\n"
				+ "ride list\n"
				+ "waiting for rides\n"
				+ "ride found from giza to cairo\n"
				+ "accept\n"
				+ "notifing client1\n");
				*/
		/*
		Storage s = new SqlDb();
		ArrayList<Offer> o = s.checkOffer("client1");
		for(Offer offer: o) {
			System.out.println(offer.price);
		}*/
		
		//SqlDb.selectAll();
		
		Register r = new Register();
		//r.register();*/
		
		/*
		System.out.println("This is A demo Program of the Uber Application");
		System.out.println("Test Features That are Supported");
		Scanner scan = new Scanner(System.in);
		while (true){
			System.out.println("1- Login");
			System.out.println("2- Register ");
			int choice = 0;
			if(scan.hasNextLine())
				choice = Integer.parseInt(scan.nextLine());
			if(choice == 1) {
				Login l = new Login();
				l.login();
			}else if(choice == 2){
				r.register();
			} else{
				scan.close();
			}
		}*/
		Scanner scanner = new Scanner(System.in);
		while (true){
			System.out.println("1- register.");
			System.out.println("2- log in.");
			System.out.println("3- Exit.");
			int answer = Integer.parseInt(scanner.nextLine());
			if(answer == 1){
				Register register = new Register();
				register.register();
			}
			else if (answer == 2){
				Login login = new Login();
				User user = login.login();
				if(user == null){
					System.out.println("Not found.");
				}else {
					user.mainmenu();
				}
			}else {
				break;
			}
		}
		scanner.close();
		System.exit(0);
	}
}
