import java.util.ArrayList;

public interface Storage {
	User getUser(String username,String password);
        User getUser(String username);
	Boolean addUser(User user);	
	boolean addDriver(Driver driver);
	boolean addClient(Client client);
	void removeUser(User user);
	Client getClient(String username);
	Driver getDriver(String username);
	ArrayList<Request> checkRides(String username);
	ArrayList<Offer> checkOffer(String username);
	void addRequest(Request request);
	void addOffer(Offer offer);
	boolean acceptOffer(String username,int requestId);
        void rateDriver(String username, int rate);
        void setEnable(String userName, int enable);
        ArrayList<String> getFav(String userName);
        void addFav(String userName, String location);
        void verify(String UserName);
        ArrayList<Driver> getnotVerifiedDriveres();
        ArrayList<User> getUsers();
        ArrayList<Request> getRequestsTo(String src);
}
        
