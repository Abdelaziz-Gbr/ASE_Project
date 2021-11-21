import java.util.Scanner;

public class Login {
	Scanner scan;
	Storage st ;

	public User login() {
		scan = new Scanner(System.in);
		System.out.println("enter user name and password");
		String username = scan.nextLine();
		String password = scan.nextLine();
		User user = st.getUser(username, password);
		if(user == null) {
			System.out.println("FaildLogin");
		}else {
			System.out.println("loginSuccessfully ");
		}
		return user;
	}
}
