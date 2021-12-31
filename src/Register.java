import java.util.Scanner;

public class Register {
	Scanner scan;
	Storage st = SqlDb.getInstance();
	public Boolean register() {
		scan = new Scanner(System.in);
		User user;
		String firstName;
		String lastName;
		System.out.println("Enter your First Name");
		firstName = scan.nextLine();
		System.out.println("Enter your Last Name");
		lastName = scan.nextLine();
		System.out.println("enter username and password");
		String username = scan.nextLine();
		String password = scan.nextLine();
		System.out.println("Choose the type of your account\n1-Client\n2-Driver");

		int choice = Integer.parseInt(scan.nextLine());

		if(choice == 2) {
			System.out.println("Enter your licence");
			String licences = scan.nextLine();
			System.out.println("Enter nationalId");
			String nationalId = scan.nextLine();

			user = new Driver(firstName , lastName, username ,password, licences, nationalId);
			user.setEnabled(false);
			if(st.addUser(user))
				return true;
		}
		else if(choice == 1) {
			user = new Client(firstName , lastName, username,password);
			if(st.addUser(user))
				return true;
		}
		System.out.println("username already exists");
		return false;
	}
}
