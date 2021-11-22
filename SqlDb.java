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
            		Driver driver = new Driver(rs.getString("username"),rs.getString("password"),true);
            		String sql2 = "Select * from Driver where username == '"+username+"'";
            		rs = conn.createStatement().executeQuery(sql2);
            		driver.setAvreage(rs.getFloat("avgrating"));
            		driver.setNationalId(rs.getString("national_id"));
            		driver.setLicence(rs.getString("licence"));
            		user = driver;
            	}
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
		return user;
	}
	
	public User getUser(String username) {
		String sql = "SELECT * FROM User where username == '" + username + "'";
		String sql2 = "Select * from Driver where username == '"+username+"'";
		User user = null;
        try {    
            ResultSet rs =  conn.createStatement().executeQuery(sql);
              
            if (rs.next()) {
            	String role = rs.getString("role");
            	if(role.equals("Admin")) {
            		user = new Admin(rs.getString("username"),rs.getString("password"));
            	}
            	else if(role.equals("Client") && rs.getBoolean("enabled")) {
            		user = new Client(rs.getString("username"),rs.getString("password"),true);
            	}
            	else if(role.equals("Driver") && rs.getBoolean("enabled")) {
            		Driver driver = new Driver(rs.getString("username"),rs.getString("password"),true);
            		rs = conn.createStatement().executeQuery(sql2);
            		driver.setAvreage(rs.getFloat("avgrating"));
            		driver.setNationalId(rs.getString("national_id"));
            		driver.setLicence(rs.getString("licence"));
            		user = driver;
            		
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
	public boolean addDriver(Driver driver) {
		String sql = "SELECT * FROM Driver where username == '" + driver.getUsername() + "'";  
		
		try {    
            ResultSet rs =  conn.createStatement().executeQuery(sql);
              
            if (rs.next()) {                                     
            	return false;
            }  
            else {          	
            	String insert = "insert into Driver (username,national_id,licence,)  \r\n"
				+ "values ('" + driver.getUsername() + "','" + driver.getNationalId() + "'," + driver.getLicence() +"');";
            	conn.createStatement().executeQuery(insert);
            }
            
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } 
		return true;
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
	public ArrayList<Request> checkRides(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Offer> checkOffer(String username) {
		String sql = "SELECT Offer.*\r\n"
				+ "  FROM Request,Offer \r\n"
				+ "  where (Request.client = '"+ username + "' and Offer.request = Request.ID) or Offer.driver == '"+username+"' ;";  
        ArrayList<Offer> out = new ArrayList<Offer>();
        try {    
            
            ResultSet rs =  conn.createStatement().executeQuery(sql);  
            while (rs.next()) {
            	Offer off = new Offer(rs.getFloat("price"),null);
            	off.setStatus(Status.valueOf(rs.getString("status")));
            	off.setRequestId(rs.getInt("request"));
            	off.setDriver((Driver) getUser(rs.getString("driver")));
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
            }  
        } catch (SQLException ex) {  
            System.out.println(ex.getMessage());  
        }  
	}

	@Override
	public void rateDriver(String username, int rate) {
		float avgrate = 0;
		int numrate = 0;
		String sql = "insert into Rates (username,rate) values('"+username+"',"+rate+") ";
		String sql2 = "select rate from Rates where username == '"+username+"'";
		String sql3 = "update Driver set avgrate = "+ avgrate + "";
		try {    
            conn.createStatement().executeQuery(sql);
            ResultSet rs = conn.createStatement().executeQuery(sql2);
            while(rs.next()) {
            	avgrate += rs.getInt("rate");
            	numrate++;
            }
            avgrate /= numrate;
            conn.createStatement().executeQuery(sql3);
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
		
	}

}
