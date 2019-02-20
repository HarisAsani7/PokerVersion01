package chatroom.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ListenerThread extends Thread {
	private static Logger logger = Logger.getLogger("");
	private final ServerSocket listener;

	public ListenerThread(int port) throws IOException  {
		super();
		this.setName("ListenerThread");
		listener = new ServerSocket(port, 10, null);
	}
	
	@Override
	public void run() {
		logger.info("Start listener");
		while (true) {
			try {
				Socket socket = listener.accept();
				Client.add(new Client(socket));
			} catch (Exception e) {
				logger.info(e.toString());
			}
		}
	}
}
