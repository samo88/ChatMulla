package server.message;


import server.Account;
import server.Client;

public class DeleteLogin extends Message {
	private String token;
	
	public DeleteLogin(String[] data) {
		super(data);
		this.token = data[1];
	}

	@Override
	public void process(Client client) {
		boolean result = false;
		if (client.getToken().equals(token)) {
			Account.remove(client.getAccount());
			client.setToken(null);
			client.setAccount(null);
			result = true;
		}
		client.send(new Result(this.getClass(), result));
	}

}
