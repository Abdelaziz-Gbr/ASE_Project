import java.util.ArrayList;

public interface Storage {
	User getUser(String username,String password);
	Boolean addUser(User user);	
	ArrayList<Driver> getEnabledDrivers();
	ArrayList<Client> getEnabledClients();
	ArrayList<Request> getAllRides();
	void addDriver(Driver driver);
	void addClient(Client client);
	void removeUser(User user);
	Client getClient(String username);
	Driver getDriver(String username);
	ArrayList<Request> checkRides(String username);
	ArrayList<Offer> checkOffer(String username);
	void addRequest(Request request);
	void addOffer(Offer offer);
}
