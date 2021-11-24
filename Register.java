import java.util.Scanner;

public class Register {
	Scanner scan;
	Storage st = SqlDb.getInstance();
	public Boolean register() {
		scan = new Scanner(System.in);
		User user;
		System.out.println("enter username and password");
		String username = scan.nextLine();
		/*String[] newUserName = username.split(" ");
		if(newUserName.length > 1){
			System.out.println("No Spaces allowed\n.. deleting the white spaces..");
			username = "";
			for(String name : newUserName)
				username += name;
		}*/
		String password = scan.nextLine();
		System.out.println("Choose the type of your account\n1-Client\n2-Driver");

		int choice = Integer.parseInt(scan.nextLine());

		if(choice == 2) {
			System.out.println("Enter your licence");
			String liences = scan.nextLine();
			System.out.println("Enter nationalId");
			String nationalId = scan.nextLine();

			user = new Driver(username, password, liences, nationalId);
			user.setEnabled(false);
			if(st.addUser(user))
				return true;
		}
		else if(choice == 1) {
			user = new Client(username,password);
			if(st.addUser(user))
				return true;
		}
		System.out.println("username already exists");
		return false;
	}
}
