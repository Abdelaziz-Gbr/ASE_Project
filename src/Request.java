
public class Request {
	
	
	
	String source;
	String destnation;
	Client client;
	Status status;
	int requestID;
	public Request(String source, String destnation, Client client) {
		super();
		this.source = source;
		this.destnation = destnation;
		this.client = client;
		status = Status.PENDING;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public String getInfo(){
		String temp = "";
		temp += "Source :" + source;
		temp += " Destination :" + destnation;
		temp += " Request ID :" + requestID;
		return temp;
	}

	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestnation() {
		return destnation;
	}
	public void setDestnation(String destnation) {
		this.destnation = destnation;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	
	
}
