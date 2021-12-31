import java.util.ArrayList;

public class RateManager {
	Storage st = SqlDb.getInstance();
	private static RateManager ins = null;
	
	private RateManager() {
	}
	
	public static RateManager getInstance() {
		if (ins == null) {
			ins = new RateManager();
		}
		return ins;
	}
	
	public String checkRates(Client client){
		return st.checkRates(client.getUsername());
	}
	
	public void rateDriver(String dusername, float rate) {
		st.rateDriver(dusername, rate);
	}
	
	public ArrayList<Integer> getRates(Driver driver){
		return st.getRates(driver.getUsername());
	}
}
