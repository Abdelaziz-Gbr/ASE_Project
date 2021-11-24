import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User {
	public Admin(String username, String password) {
		super(username, password, false);
	}

	public void mainmenu(){
		System.out.println("Welcome!");
		Scanner scanner = new Scanner(System.in);
		while(true){
			System.out.println("1- list suspended users");
			System.out.println("2- list unsuspended users");
			System.out.println("3- list un-verified drivers");
			System.out.println("4- Verify a driver");
			System.out.println("5- Suspend a user");
			System.out.println("6- log out");
			int ans = Integer.parseInt(scanner.nextLine());
			if(ans == 1){
				ArrayList<User> users = SqlDb.getInstance().getUsers();
				for(User user : users)
					if(!user.getEnabled())
						System.out.println(user.getUsername());
			}
			else if(ans == 2){
				ArrayList<User> users = SqlDb.getInstance().getUsers();
				for(User user : users)
					if(user.getEnabled())
						System.out.println(user.getUsername());

			}
			else if(ans == 3){
				ArrayList<Driver> drivers = SqlDb.getInstance().getnotVerifiedDriveres();
				if(drivers.size() < 1) {
					System.out.println("No Unverified Drivers at the moment");
				}
				else {
					for(Driver driver : drivers){
						System.out.println(driver.getUsername());
					}
				}
			}
			else if(ans == 4){
				System.out.println("Enter User Name");
				String userName = scanner.nextLine();
				SqlDb.getInstance().verify(userName);
			}
			else if(ans == 5){
				System.out.println("Enter User name of the user to be suspended");
				String userName = scanner.nextLine();
				suspend(SqlDb.getInstance().getUser(userName));
			}
			else if(ans == 6){
				break;
			}
			else{
				System.out.println("Invalid input!");
			}
		}
		return;
	}

	public void suspend(User user) {
		if (!(user instanceof Admin)) {
			user.setEnabled(false);
			SqlDb.getInstance().setEnable(user.getUsername(), 0);
		}else{
			System.out.println("You can not suspend anther admin!");
		}
		
	}
	
	public void unsuspend(User user){
		if (!(user instanceof Admin)) {
			user.setEnabled(true);
			SqlDb.getInstance().setEnable(user.getUsername(), 1);
		}else{
			System.out.println("You can not unsuspended anther admin!");
		}

	}
	
	public void accept(Driver driver) {
			driver.setEnabled(true);
			SqlDb.getInstance().setEnable(driver.getUsername(), 1);
	}
}
