
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlDb implements Storage {

    public static String url = "jdbc:sqlite:" + System.getProperty("user.dir")+"\\src\\DataBase\\db1.db";
    private static Connection conn;

    private static SqlDb ins = null;

    private SqlDb() {
        conn=connect();
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
        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);

            if (rs.next()) {
                String role = rs.getString("role");
                if (role.equals("Admin")) {
                    user = new Admin(rs.getString("username"), rs.getString("password"));
                } else if (role.equals("Client") && rs.getBoolean("enabled")) {
                    user = new Client(rs.getString("username"), rs.getString("password"), true);
                } else if (role.equals("Driver") && rs.getBoolean("enabled")) {
                    Driver driver = new Driver(rs.getString("username"), rs.getString("password"), true);
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
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                return false;
            } else {
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
    public boolean addDriver(Driver driver) {
        String sql = "SELECT * FROM Driver where username == '" + driver.getUsername() + "'";

        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);

            if (rs.next()) {
                return false;
            } else {
                String insert = "insert into Driver (username,national_id,licence,)  \r\n"
                        + "values ('" + driver.getUsername() + "','" + driver.getNationalId() + "'," + driver.getLicence() + "');";
                conn.createStatement().executeUpdate(insert);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean addClient(Client client) {
        String sql = "SELECT * FROM Client where username == '" + client.getUsername() + "'";

        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);

            if (rs.next()) {
                return false;
            } else {
                String insert = "insert into Client (username)  \r\n"
                        + "values ('" + client.getUsername() + "');";
                conn.createStatement().executeUpdate(insert);
            }

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

    @Override
    public ArrayList<Offer> checkOffer(String username) {
        String sql = "SELECT distinct Offer.*\r\n"
                + "  FROM Request,Offer \r\n"
                + "  where (Request.client = '" + username + "' and Offer.request = Request.ID) or Offer.driver == '" + username + "' ;";
        ArrayList<Offer> out = new ArrayList<Offer>();
        try {

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                Offer off = new Offer(rs.getFloat("price"), null);
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
        String sql = "Insert into request (client,source,destination) values('" + request.getClient().getUsername() + "','" + request.getSource() + "','" + request.getDestnation() + "')";

        try {
            conn.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void addOffer(Offer offer) {
        String sql = "Insert into offer (price,driver,request) values('" + offer.getPrice() + "','" + offer.getDriver().getUsername() + "','" +offer.getRequestId()+ "')";

        try {

            conn.createStatement().executeUpdate(sql);

            // loop through the result set  
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public boolean acceptOffer(String username, int requestId) {
        String sql = "update Offer set status = 'ACCEPTED' where driver = '" + username + "' and request = " + requestId + ";";

        try {
            conn.createStatement().executeUpdate(sql);
            sql = "delete from Offer where driver= '"+username+"'and status != 'ACCEPTED'";
            conn.createStatement().executeUpdate(sql);
            sql = "update Request set status = 'STARTED' where request = " + requestId + ";";
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void close() {
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
        String sql = "insert into Rates (username,rate) values('" + username + "'," + rate + ") ";
        String sql2 = "select rate from Rates where username == '" + username + "'";
        String sql3 = "update Driver set avgrate = " + avgrate + "";
        try {
            conn.createStatement().executeUpdate(sql);
            ResultSet rs = conn.createStatement().executeQuery(sql2);
            while (rs.next()) {
                avgrate += rs.getInt("rate");
                numrate++;
            }
            avgrate /= numrate;
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
        try {
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
        try {
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
        try {
            conn.createStatement().executeUpdate(Query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addFav(String userName, String location) {
        String query = "INSERT INTO Locations (driver, location) values ('" + userName + "', '" + location + "')";
        try {
            conn.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getFav(String userName) {
        ArrayList<String> favs = new ArrayList<String>();
        String query = "SELECT * FROM Locations WHERE driver = '" + userName + "'";
        try {

            ResultSet resultSet = conn.createStatement().executeQuery(query);
            while (resultSet.next()) {
                favs.add(resultSet.getString(2));
            }
        } catch (SQLException e) {
            System.out.println("null return");
            e.printStackTrace();
        }
        return favs;
    }

    @Override
    public void setEnable(String userName, int enable) {
        try {
            conn.createStatement().executeUpdate("UPDATE User SET enabled = " + enable + " where username = '" + userName + "'");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public ArrayList<Request> getRequestsTo(String src){
		String sql = "SELECT * from Request WHERE source = '" + src + "'and status='PENDING'";
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
    @Override
    public void setRequestStatus(int requestId,Status status)
    {
        String sql = "update Request set status = '"+status.toString()+"'where ID="+requestId+";";
        try {
            conn.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SqlDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void removeOffer(Offer offer)
    {
        String sql = "delete from Offer where driver = '"+offer.getDriver().getUsername()+"'and request="+offer.getRequestId()+";";
        try {
            conn.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SqlDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
