import java.util.ArrayList;

public class RequestManager {
	private static RequestManager ins = null;
	private Storage st = SqlDb.getInstance();
	private RequestManager() {
		
	}
	
	public static RequestManager getInstance() {
		if (ins == null) {
			ins = new RequestManager();
		}
		return ins;
	}

	public ArrayList<Request> getRequestsTo(String place){
		ArrayList<Request> requests = SqlDb.getInstance().getRequestsTo(place);
		return requests;
	}

	public void makeRequest(Request request) {
		st.addRequest(request);
	}
	
	public void makeOffer(Offer offer) {
		st.addOffer(offer);
	}
	
	public void acceptOffer(Offer offer) {
		if(st.acceptOffer(offer.getDriver().getUsername(),offer.getRequestId())) {
			offer.setStatus(Status.ACCEPTED);
		}
		
	}
	
	public void endRequest(Request request) {
		st.addRequest(request);
	}
	
	public ArrayList<Offer> checkOffers(User user){
		return st.checkOffer(user.username);
	}

	
}
