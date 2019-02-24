package chatroom.server;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
	private static final Logger logger = Logger.getLogger("");
	private static int port = -1;
	private static boolean secure = true;
	
	public static void main(String[] args) {
		logger.setLevel(Level.FINE);
		logger.info("Read any existing data");
		Chatroom.readChatrooms();
		Account.readAccounts();
		
		try {
			// Read options: port and security
			readOptions();
			
			// Start the listener
			ListenerThread lt = new ListenerThread(port, secure);
			lt.start();
			
			// Start the clean-up thread
			CleanupThread ct = new CleanupThread();
			ct.start();
		} catch (IOException e) {
			if (secure && e.getCause() instanceof GeneralSecurityException) {
				logger.severe("Error creating secure socket connection - does the keystore exist?");
			}
			logger.info(e.toString());
		}
	}

	private static void readOptions() {
		try (Scanner in = new Scanner(System.in)) {
			while (port < 1024 || port > 65535) {
				System.out.println("Enter the port number (1024-65535): ");
				String s = in.nextLine();
				try {
					port = Integer.parseInt(s);
				} catch (NumberFormatException e) {
					// do nothing
				}
			}
			
			System.out.println("Enter 'yes' if the server should use SecureSockets");
			String s = in.nextLine().trim();
			secure = s.equalsIgnoreCase("yes");
		}
	}
}
