package ch.fhnw.richards.chatroom.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
	private static final Logger logger = Logger.getLogger("");
	private static final int port = 31415;
	
	public static void main(String[] args) {
		logger.setLevel(Level.FINE);
		logger.info("Read any existing data");
		Chatroom.readChatrooms();
		Account.readAccounts();
		
		try {
			// Start the listener
			ListenerThread lt = new ListenerThread(port);
			lt.start();
			
			// Start the clean-up thread
			CleanupThread ct = new CleanupThread();
			ct.start();
		} catch (IOException e) {
			logger.info(e.toString());
		}
	}

}
