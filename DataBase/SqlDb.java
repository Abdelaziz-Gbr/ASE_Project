import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlDb implements Storage{
	
	
	public static String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\db1.db"; 
	private static Connection conn = connect();
	
	private static SqlDb ins = null;
	
	private SqlDb() {
		connect();
	}
	
	public static SqlDb getInstance() {
		if (ins == null) {
			ins = new SqlDb();
		}
		return ins;
	}
	
	public static Connection connect() {  
        
        try {  
            // db parameters  
        	 
            // create a connection to the database  
            
            System.out.println("Connection to SQLite has been established.");  
            return DriverManager.getConnection(url);  
              
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
        return null;
    } 
	
	@Override
	public User getUser(String username, String password) {
		String sql = "SELECT * FROM User where username == '" + username + "' and password == '" + password + "'";  
		User user = null;
        try {    
            ResultSet rs =  conn.createStatement().executeQuery(sql);
              
            if (rs.next()) {                                     
            	if(rs.getString("role").equals("Admin")) {
            		user = new Admin(rs.getString("username"),rs.getString("password"));
            	}
            	else if(rs.getString("role").equals("Client") && rs.getBoolean("enabled")) {
            		user = new Client(rs.getString("username"),rs.getString("password"),true);
            	}
            	else if(rs.getString("role").equals("Driver") && rs.getBoolean("enabled")) {
            		user = new Driver(rs.getString("username"),rs.getString("password"),true);
            	}
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
		return user;
	}

	@Override
	public Boolean addUser(User user) {
		String sql = "SELECT * FROM User where username == '" + user.getUsername() + "'";  
		
		try {    
            ResultSet rs =  conn.createStatement().executeQuery(sql);
              
            if (rs.next()) {                                     
            	return false;
            }  
            else {           	
            	String insert = "insert into User (username,password,enabled,role) values (?,?,?,?);";
            	PreparedStatement ins = conn.prepareStatement(insert);
            	ins.setString(1, user.getUsername());
            	ins.setString(2, user.getPassword());
                ins.setBoolean(3, user.getEnabled());
                ins.setString(4, user.getClass().getName());
                ins.executeUpdate();
            }
        } catch (SQLException e) {  
            System.out.println(e.getMessage());
            return false;
        } 
		return true;
	}

	@Override
	public ArrayList<Driver> getEnabledDrivers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Client> getEnabledClients() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Request> getAllRides() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addDriver(Driver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addClient(Client client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Client getClient(String username) {
		String sql = "SELECT * FROM Client where username == '" + username + "'";  
        
        try {    
            
            ResultSet rs =  conn.createStatement().executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {  
                System.out.println(rs.getString("username") +  "\t" +   
                                   rs.getString("password") + "\t" +  
                                   rs.getBoolean("enabled"));  
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } 
		return null;
	}

	@Override
	public Driver getDriver(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Request> checkRides(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Offer> checkOffer(String username) {
		String sql = "SELECT Offer.*\r\n"
				+ "  FROM Request,Offer \r\n"
				+ "  where (Request.client = '"+ username + "' and Offer.request = Request.ID) or Offer.username == '"+username+"' ;";  
        ArrayList<Offer> out = new ArrayList<Offer>();
        try {    
            
            ResultSet rs =  conn.createStatement().executeQuery(sql);  
            while (rs.next()) {
            	Offer off = new Offer(rs.getFloat("price"),getDriver(rs.getString("driver")));
            	off.setStatus(Status.valueOf(rs.getString("status")));
            	off.setRequestId(rs.getInt("requestId"));
                out.add(off);
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } 
		return out;
	}

	@Override
	public void addRequest(Request request) {
		String sql = "Insert into request (client,source,destination) values('"+request.getClient().getUsername()+"','" +request.getSource()+"','"+request.getDestnation()+"')";  
        
        try {    
            conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } 
		
	}

	@Override
	public void addOffer(Offer offer) {
		String sql = "Insert into offer (price,driver,destination) values('"+offer.getPrice()+"','" +offer.getDriver().getUsername()+"','"+"')";
        
        try {    
            
            ResultSet rs =  conn.createStatement().executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {  
                System.out.println(rs.getString("username") +  "\t" +   
                                   rs.getString("password") + "\t" +  
                                   rs.getBoolean("enabled"));  
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
		
	}

	@Override
	public boolean acceptOffer(String username, int requestId) {
		String sql = "update Offer set status = 'ACCEPTED' where username = '"+username+"' and requestId = " + requestId + ";";
        
        try {    
            conn.createStatement().executeQuery(sql);
            return true;
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
		return false;
	}
	
	public static void close()  
	{  
		try {  
            if (conn != null) {  
                conn.close(); 
                System.out.println("Connection Closed");  
            }  
        } catch (SQLException ex) {  
            System.out.println(ex.getMessage());  
        }  
	}

}
