
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlDb implements Storage {

    public static String url = "jdbc:sqlite:" + System.getProperty("user.dir")+"\\db1.db";
    private static SqlDb ins = null;

    private SqlDb() {
        
    }

    public static void main(String args[]){
        SqlDb sd = SqlDb.getInstance();
        User omar = sd.getUser("omar552001");
        System.out.println(omar.getPassword());
    }

    public static SqlDb getInstance() {
        if (ins == null) {
            ins = new SqlDb();
        }
        return ins;
    }

    public static Connection connect() {
        try {
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
        try (Connection conn = connect()) {
            ResultSet rs = conn.createStatement().executeQuery(sql);

            if (rs.next()) {
                if (rs.getString("role").equals("Admin")) {
                    user = new Admin(rs.getString("username"), rs.getString("password"));
                } else if (rs.getString("role").equals("Client") && rs.getBoolean("enabled")) {
                    user = new Client(rs.getString("username"), rs.getString("password"), true);
                } else if (rs.getString("role").equals("Driver") && rs.getBoolean("enabled")) {
                    Driver driver = new Driver(rs.getString("username"), rs.getString("password"), true);
                    String sql2 = "Select * from Driver where username == '" + username + "'";
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
    public User getUser(String username) {
        String sql = "SELECT * FROM User where username == '" + username + "'";
        String sql2 = "Select * from Driver where username == '" + username + "'";
        User user = null;
        try (Connection conn = connect()) {
            ResultSet rs = conn.createStatement().executeQuery(sql);

            if (rs.next()) {
                String role = rs.getString("role");
                if (role.equals("Admin")) {
                    user = new Admin(rs.getString("username"), rs.getString("password"));
                } else if (role.equals("Client") && rs.getBoolean("enabled")) {
                    user = new Client(rs.getString("username"), rs.getString("password"), true);
                } else if (role.equals("Driver") && rs.getBoolean("enabled")) {
                    Driver driver = new Driver(rs.getString("username"), rs.getString("password"), true);

                    ResultSet rs2 = conn.createStatement().executeQuery(sql2);
                    driver.setAvreage(rs2.getFloat("avgrating"));
                    driver.setNationalId(rs2.getString("national_id"));
                    driver.setLicence(rs2.getString("licence"));
                    user = driver;

                }
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }


    public String getFirstName(String userName){
        String firstName = "NULL";

        String query = "SELECT firstname FROM User WHERE username = '" + userName + "'";
        try {
            ResultSet resultSet = connect().createStatement().executeQuery(query);
            firstName = resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firstName;
    }

    public String getLastName(String userName){
        String lastname = "NULL";

        String query = "SELECT lastname FROM User WHERE username = '" + userName + "'";
        try {
            ResultSet resultSet = connect().createStatement().executeQuery(query);
            lastname = resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastname;
    }

    public Boolean addUser(User user) {
        String sql = "SELECT * FROM User where username == '" + user.getUsername() + "'";
        try (Connection conn = connect()) {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                return false;
            } 
            else {
                String insert = "insert into User (firstname, lastname, username,password,enabled,role) values (?,?,?,?,?,?);";
                PreparedStatement ins = conn.prepareStatement(insert);
                ins.setString(1, user.getFirstname());
                ins.setString(2, user.getLastname());
                ins.setString(3, user.getUsername());
                ins.setString(4, user.getPassword());
                ins.setBoolean(5, user.getEnabled());
                ins.setString(6, user.getClass().getName());
                ins.executeUpdate();
                if(user instanceof  Client){
                    addClient((Client) user);
                }
                if(user instanceof Driver)
                	addDriver((Driver)user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean addDriver(Driver driver) {
        String sql = "SELECT * FROM Driver where username == '" + driver.getUsername() + "'";

        try (Connection conn = connect()) {
            ResultSet rs = conn.createStatement().executeQuery(sql);

            if (rs.next()) {
                return false;
            } else {
                String insert = "insert into Driver (username,national_id,licence)"
                        + "values ('" + driver.getUsername() + "','" + driver.getNationalId() + "','" + driver.getLicence() + "');";
                conn.createStatement().executeUpdate(insert);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean addClient(Client client) {

        String sql = "insert into Client (username)  \r\n"
                + "values ('" + client.getUsername() + "');";


        try (Connection conn = connect()) {
            conn.createStatement().executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;

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

    public LocalDate getClientDate(String userName){
        String query = "SELECT BirthDate FROM Client WHERE username = '" + userName + "'";
        LocalDate date = LocalDate.now();
        try (Connection conn = connect()){
            ResultSet rs = conn.createStatement().executeQuery(query);
            date =LocalDate.parse(rs.getString("BirthDate"));

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return date;
    }

    public ArrayList<String> getDiscountAreas(){
        ArrayList<String> areas = new ArrayList<String>();
        String query = "SELECT Name FROM onDiscountAreas";
        try (Connection conn = connect()){
            ResultSet rs = conn.createStatement().executeQuery(query);
            while(rs.next()){
                areas.add(rs.getString("Name"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return areas;
    }

    public boolean FirstRide(String userName){
        boolean firstRide = false;

        String query = "SELECT FirstRide FROM Client WHERE username = '" + userName + "'";
        try(Connection conn = connect()){
            ResultSet rs = conn.createStatement().executeQuery(query);
            firstRide = rs.getBoolean("FirstRide");
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return firstRide;
    }

    public void updatePrice(String driver, int req, double price){
        String query = "UPDATE Offer SET ClientPrice = '"+ price + "' WHERE driver = '" + driver + "' AND request = '"+ req + "'";

        try(Connection conn = connect()){
            conn.createStatement().executeUpdate(query);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void CalculateClientPrice(String username) {
        String sql = "SELECT distinct Offer.Price AS Price, Request.destination As Destination, Request.Date AS Date, Request.Passengeres As Passengers, Offer.Driver As Driver, Request.ID AS ID\r\n"
                + "  FROM Request,Offer \r\n"
                + "  where Request.client = '" + username + "' AND Request.ID = Offer.request";

        try (Connection conn = connect()) {

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                double Price = rs.getFloat("Price");
                String date= rs.getString("Date");
                String destination = rs.getString("Destination");
                int passengers = rs.getInt("Passengers");
                if(DiscountCalculater.birthDate(LocalDate.parse(date), getClientDate(username))){
                    System.out.println("Happy birthDay!");
                    Price -= 0.1*Price;
                }

                if(DiscountCalculater.PublicHoliday(LocalDate.parse(date))){
                    System.out.println("Happy 18th of June!!");
                    Price -= 0.05*Price;
                }

                if(DiscountCalculater.TwoPassenger(passengers)){
                    System.out.println("Have a Nice Companion!");
                    Price -= 0.05*Price;
                }


                if(DiscountCalculater.onDiscount(destination)){
                    System.out.println("Have a Nice Ride!");
                    Price -= 0.1*Price;
                }

                if(DiscountCalculater.FirstRide(username)){
                    System.out.println("Hope you enjoy it!");
                    Price -= 0.1*Price;
                }

                updatePrice(rs.getString("Driver"), rs.getInt("ID"), Price);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public ArrayList<Offer> checkOffer(String username) {
        String sql = "SELECT distinct Offer.*\r\n"
                + "  FROM Request,Offer \r\n"
                + "  where (Request.client = '" + username + "' and Offer.request = Request.ID) or Offer.driver == '" + username + "' ;";
        ArrayList<Offer> out = new ArrayList<Offer>();
        try (Connection conn = connect()) {

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                Offer off = new Offer(rs.getFloat("ClientPrice"), null);
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

    public void setBirthDay(String username, java.sql.Date date){
        String query = "UPDATE Client SET BirthDate = '"+ date + "' WHERE username = '"+ username + "'";
        try (Connection conn = connect()){
            conn.createStatement().executeUpdate(query);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addRequest(Request request) {
        String sql = "Insert into request (client,source,destination, Date, Passengeres) values('" + request.getClient().getUsername() + "','" + request.getSource() + "','" + request.getDestnation() + "', '" + java.time.LocalDate.now() +"','"+request.num+"')";

        try (Connection conn = connect()) {
            conn.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void addOffer(Offer offer) {
        String sql = "Insert into offer (price,driver,request) values('" + offer.getPrice() + "','" + offer.getDriver().getUsername() + "','" +offer.getRequestId()+ "')";

        try (Connection conn = connect()) {

            conn.createStatement().executeUpdate(sql);

            // loop through the result set  
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void checkFirstTime(int requestID){
        String userName = getClientFromReqID(requestID);
        String query = "UPDATE Client SET FirstRide = 0 WHERE username = '"+ userName + "' ";

        try(Connection conn = connect()) {
            conn.createStatement().executeUpdate(query);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getClientFromReqID(int requestID){
        String query = "SELECT DISTINCT client FROM Request WHERE ID = '"+ requestID +"'";
        String username = "";
        try(Connection conn = connect()) {
            ResultSet rs = conn.createStatement().executeQuery(query);
            username = rs.getString("client");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return username;
    }
    @Override
    public boolean acceptOffer(String username, int requestId) {

        String sql = "update Offer set status = 'ACCEPTED' where driver = '" + username + "' and request = " + requestId + ";";
        try (Connection conn = connect()){
            conn.createStatement().executeUpdate(sql);
            sql = "delete from Offer where driver= '"+username+"'and status != 'ACCEPTED'";
            conn.createStatement().executeUpdate(sql);
            sql = "update Request set status = 'STARTED', driver = '"+username+"' where ID = " + requestId + ";";
            conn.createStatement().executeUpdate(sql);
            conn.close();
            checkFirstTime(requestId);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void close() {
        try (Connection conn = connect()) {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void rateDriver(String username, float rate) {
        System.out.println("USer name = " + username + " rate = " + rate);
        float avgrate = 0;
        int numrate = 0;
        String sql = "insert into Rates (username,rate) values('" + username + "'," + rate + ") ";
        String sql2 = "select rate from Rates where username == '" + username + "'";
        try (Connection conn = connect()) {
            conn.createStatement().executeUpdate(sql);
            ResultSet rs = conn.createStatement().executeQuery(sql2);
            while (rs.next()) {
                avgrate += rs.getInt("rate");
                numrate++;
            }
            avgrate /= numrate;
            String sql3 = "update Driver set avgrating = " + avgrate + " WHERE username =  '" + username +"'";
            conn.createStatement().executeUpdate(sql3);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Client getClient(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Driver getDriver(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> temp = new ArrayList<User>();
        try (Connection conn = connect()) {
            ResultSet rs = conn.createStatement().executeQuery("Select * from User");
            while (rs.next()) {
                temp.add(new User(rs.getString("username"), " ", rs.getBoolean("enabled")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    @Override
    public ArrayList<Driver> getnotVerifiedDriveres() {
        ArrayList<Driver> temp = new ArrayList<Driver>();
        try (Connection conn = connect()) {
            ResultSet rs = conn.createStatement().executeQuery("Select * from Driver where verified = 0");
            while (rs.next()) {
                temp.add(new Driver(rs.getString("username"), " ", false));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    @Override
    public void verify(String UserName) {
        setEnable(UserName, 1);
        String Query = "UPDATE Driver SET verified = 1 WHERE username = '" + UserName + "'";
        try (Connection conn = connect()) {
            conn.createStatement().executeUpdate(Query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addFav(String userName, String location) {
        String query = "INSERT INTO Locations (driver, location) values ('" + userName + "', '" + location + "')";
        try (Connection conn = connect()) {
            conn.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getFav(String userName) {
        ArrayList<String> favs = new ArrayList<String>();
        String query = "SELECT * FROM Locations WHERE driver = '" + userName + "'";
        try (Connection conn = connect()) {

            ResultSet resultSet = conn.createStatement().executeQuery(query);
            while (resultSet.next()) {
                favs.add(resultSet.getString(2));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("null return");
            e.printStackTrace();
        }
        
        return favs;
    }

    @Override
    public void setEnable(String userName, int enable) {
        try (Connection conn = connect()) {
            conn.createStatement().executeUpdate("UPDATE User SET enabled = " + enable + " where username = '" + userName + "'");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public ArrayList<Request> getRequestsTo(String src){
		String sql = "SELECT * from Request WHERE source = '" + src + "'and status='PENDING'";
		ArrayList<Request> requests = new ArrayList<Request>();
		try (Connection conn = connect()) {

			ResultSet rs =  conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				Request req = new Request(rs.getString("source") ,rs.getString("destination"), null );
				req.setRequestID(rs.getInt("ID"));
				requests.add(req);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return requests;
	}
    
    @Override
    public void setRequestStatus(int requestId,Status status)
    {
        String sql = "update Request set status = '"+status.toString()+"'where ID="+requestId+";";
        try (Connection conn = connect()) {
            conn.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void removeOffer(Offer offer)
    {
        String sql = "delete from Offer where driver = '"+offer.getDriver().getUsername()+"'and request="+offer.getRequestId()+";";
        try (Connection conn = connect()) {
            conn.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SqlDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String checkRates(String cusername) {
    	String sql = "select * from Request where client = '"+ cusername +"' and status = 'ENDED'";
    	String sql2 = "delete from Request where client = '"+ cusername +"' and status = 'ENDED'";
        try (Connection conn = connect()) {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if(rs.next()) {
            	conn.createStatement().executeUpdate(sql2);
            	return rs.getString("driver");
            }
            rs.close();
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
		return null;
    }

	@Override
	public ArrayList<Integer> getRates(String username) {
		String sql = "Select Distinct rate from Rates where username = '"+username+"'";
		ArrayList<Integer> rates = new ArrayList<Integer>();
		try (Connection conn = connect()) {
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while(rs.next()) {
				rates.add(rs.getInt("rate"));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return rates;
	
	}

    public void addOnDiscountAreas(String Area){
        String sql = "INSERT INTO onDiscountAreas (name) values ('" +Area+"')";
        try(Connection conn = connect()){
            conn.createStatement().executeUpdate(sql);
        }
        catch (SQLException e){
            System.err.println(e);
        }
    }
    public void DeleteFromDiscountAreas(String Area){
        String sql = "DELETE FROM onDiscountAreas WHERE name = '" +Area+"'";
        try(Connection conn = connect()){
            conn.createStatement().executeUpdate(sql);
        }
        catch (SQLException e){
            System.err.println(e);
        }
    }

}
