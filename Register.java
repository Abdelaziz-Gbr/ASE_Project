import java.util.Scanner;

public class Register {
	Scanner scan;
	Storage st ;
	public Boolean register() {
		scan = new Scanner(System.in);
		User user;
		System.out.println("enter username and password");
		String username = scan.nextLine();
		String password = scan.nextLine();
		System.out.println("Choose the type of your account\n1-Client\n2-User");

		int choice = Integer.parseInt(scan.nextLine());

		if(choice == 2) {
			System.out.println("Enter your licence");
			String liences = scan.nextLine();
			System.out.println("Enter nationalId");
			String nationalId = scan.nextLine();

			user = new Driver(username, password, liences, nationalId);
			user.setEnabled(false);
			st.addUser(user);
			return true;
		}
		else if(choice == 1) {
			user = new Client(username,password);
			st.addUser(user);
			return true;
		}
		return false;
	}
}
