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
	
	ArrayList<Integer> checkRates(Client client){
		return null;
		
	}// for the client
	
}
