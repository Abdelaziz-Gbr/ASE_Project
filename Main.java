import java.util.ArrayList;

public class Main {

	
	public static void main(String args[]) {
		//SqlTest.selectAll();
		
		//SqlTest.close();
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
		Storage s = new SqlTest();
		ArrayList<Offer> o = s.checkOffer("client1");
		for(Offer offer: o) {
			System.out.println(offer.price);
		}*/
		
		//SqlTest.selectAll();
		

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
			Register r = new Register();
			r.register();
		} else{
			scan.close();
		}
		}
}
