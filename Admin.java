public class Admin extends User {
	public Admin(String username, String password) {
		super(username, password, false);
	}
	
	public void suspend(User user) {
		if (!(user instanceof Admin)) {
			user.setEnabled(false);
		}
		
	}
	
	public void unsuspend(User user) {
		if (!(user instanceof Admin)) {
			user.setEnabled(true);
		}
	}
	
	public void accept(Driver driver) {
		driver.setEnabled(true);
	}
}
