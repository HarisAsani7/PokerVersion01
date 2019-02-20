package ch.fhnw.richards.chatroom.server.message;

import java.util.ArrayList;

import ch.fhnw.richards.chatroom.server.Chatroom;
import ch.fhnw.richards.chatroom.server.Client;

public class ListChatrooms extends Message {
	private String token;
	
	public ListChatrooms(String[] data) {
		super(data);
		this.token = data[1];
	}

	@Override
	public void process(Client client) {
		if (client.getToken().equals(token)) {
			ArrayList<String> names = Chatroom.listPublicNames();
			client.send(new Result(true, names));
		} else {
			client.send(new Result(false));
		}
	}
}
