package ch.fhnw.richards.chatroom.server.message;

import ch.fhnw.richards.chatroom.server.Client;

public class Logout extends Message {

	public Logout(String[] data) {
		super(data);
	}
	
	@Override
	public void process(Client client) {
		client.setToken(null); // Destroy authentication token
		client.send(new Result(true));
	}
}
