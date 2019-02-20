package ch.fhnw.richards.chatroom.server.message;

import ch.fhnw.richards.chatroom.server.Client;

public class MessageText extends Message {

	public MessageText(String name, String target, String message) {
		super(new String[] {"MessageText", name, target, message});		
	}
	
	/**
	 * This message type does no processing at all
	 */
	@Override
	public void process(Client client) {
	}
}
