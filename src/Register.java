import java.sql.Date;
import java.util.Calendar;
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
			System.out.println("Enter The day Of your BirthDay");
			int day = Integer.parseInt(scan.nextLine());
			System.out.println("Enter The Month Of your BirthDay");
			int month = Integer.parseInt(scan.nextLine());
			java.sql.Date date = Date.valueOf("1991-"+ month + "-" + day);// don't mind the year is we don't care about it.
			//java.sql.Date date= new java.sql.Date(System.currentTimeMillis());
			user = new Client(firstName , lastName, username,password);
			if(st.addUser(user)) {
				SqlDb.getInstance().setBirthDay(username , date);
				return true;
			}
		}
		System.out.println("username already exists");
		return false;
	}
}
