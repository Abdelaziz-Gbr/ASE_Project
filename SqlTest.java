import java.awt.image.AreaAveragingScaleFilter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;  

public class SqlTest implements Storage{
	public static String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\db1.db"; 
	private static Connection conn = connect();
	
	private static SqlTest ins = null;
	
	private SqlTest() {
		connect();
	}
	
	public static SqlTest getInstance() {
		if (ins == null) {
			ins = new SqlTest();
		}
		return ins;
	}
	
	
	public static void createNewDatabase() {
        try {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
	
	public static void selectAll(){  
        String sql = "SELECT * FROM User";  
         System.out.println("trying");
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
	
	public ArrayList<Request> getRequestsTo(String src){
		String sql = "SELECT * from Request WHERE source = '" + src + "'";
		ArrayList<Request> requests = new ArrayList<Request>();
		try {

			ResultSet rs =  conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				Request req = new Request(rs.getString("source") ,rs.getString("destination"), null );
				req.setRequestID(rs.getInt("ID"));
				requests.add(req);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return requests;
	}
	
    /** 
     * @param args the command line arguments 
     */  
    public static void main(String[] args) {  
    	
    	
    	
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

	public User getUser(String username) {
		String sql = "SELECT * FROM User where username == '" + username + "'";
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

	public ArrayList<User> getUsers(){
		ArrayList<User> temp = new ArrayList<User>();
		try {
			ResultSet rs = conn.createStatement().executeQuery("Select * from User");
			while(rs.next()){
				temp.add(new User(rs.getString("username"), " " ,rs.getBoolean("enabled")));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return temp;
	}

	public ArrayList<Driver> getnotVerifiedDriveres(){
		ArrayList<Driver> temp = new ArrayList<Driver>();
		try {
			ResultSet rs = conn.createStatement().executeQuery("Select * from Driver where verified = 0");
			while(rs.next()){
				temp.add(new Driver(rs.getString("username"), " " ,false));
			}


		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return temp;
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

	public void verify(String UserName){
		setEnable(UserName, 1);
		String Query = "UPDATE Driver SET verified = 1 WHERE username = '" + UserName + "'";
		try {
			conn.createStatement().executeUpdate(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
				if(user instanceof Driver){
					String Query = "Insert into Driver (username , national_id, licence , verified) values (?,?,?,?)";
					PreparedStatement preparedStatement = conn.prepareStatement(Query);
					preparedStatement.setString(1, user.getUsername());
					preparedStatement.setString(2, ((Driver) user).getNationalId());
					preparedStatement.setString(3, ((Driver) user).getLicence());
					preparedStatement.setString(4, "0");
					preparedStatement.executeUpdate();
				}
            }
        } catch (SQLException e) {  
            System.out.println(e.getMessage());
            return false;
        } 
		return true;
	}

	@Override
	public void removeUser(User user) {
		// TODO Auto-generated method stub
		
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
		 String sql = "SELECT * FROM User where username == '" + username + "'";;  
         
        try {    
            
            ResultSet rs =  conn.createStatement().executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {  
            	System.out.println(rs.getString("username")+   
                        rs.getString("password") + 
                        rs.getBoolean("enabled") );
                return new Driver(rs.getString("username"),   
                                   rs.getString("password"), 
                                   rs.getBoolean("enabled"));  
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } 
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
		String sql = "Insert into offer (price,driver, request, status) values("+offer.getPrice()+",'" +offer.getDriver().getUsername()+"',"+ offer.getRequestId() + ", 'PENDING'" + ")";
        
        try {

			conn.createStatement().executeUpdate(sql);

		} catch (SQLException e) {
            System.out.println(e.getMessage());  
        } 
	
		
	}

	public void addFav(String userName, String location){
		String query = "INSERT INTO Locations (driver, location) values ('" + userName + "', '" + location+"')";
		try {
			conn.createStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getFav(String userName){
		ArrayList<String> favs = new ArrayList<String>();
		String query = "SELECT * FROM Locations WHERE driver = '" + userName + "'";
		try {

			ResultSet resultSet = conn.createStatement().executeQuery(query);
			while (resultSet.next()){
				favs.add(resultSet.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  favs;
	}

	public void setEnable(String userName , int enable){
		try {
			conn.createStatement().executeUpdate("UPDATE User SET enabled = " +  enable +  " where username = '" + userName + "'");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void addClient(Client client) {
		
		
	}

	@Override
	public boolean acceptOffer(String username,int requestId) {
		String sql = "update Offer set status = 'ACCEPTED' where username = '"+username+"' and requestId = " + requestId + ";";
        
        try {    
            conn.createStatement().executeQuery(sql);
            return true;
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
		return false;
	}  
}
