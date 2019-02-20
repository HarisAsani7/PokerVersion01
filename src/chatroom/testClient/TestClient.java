package chatroom.testClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * This is a really simple test client: It reads from the scanner, sends to the
 * server, and prints the server's response. This is all in plain-text - it is
 * up to the user to type in correctly formatted messages.
 */
public class TestClient {

	public static void main(String[] args) {
		String ipAddress = null;
		int portNumber = 0;

		try (Scanner in = new Scanner(System.in)) {
			boolean valid = false;

			// Read IP address
			while (!valid) {
				System.out.println("Enter a valid IP address");
				ipAddress = in.nextLine();
				valid = validateIpAddress(ipAddress);
			}

			// Read port number
			valid = false;
			while (!valid) {
				System.out.println("Enter a valid port number");
				String strPort = in.nextLine();
				valid = validatePortNumber(strPort);
				if (valid) portNumber = Integer.parseInt(strPort);
			}

			try (Socket socket = new Socket(ipAddress, portNumber);
					BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					OutputStreamWriter socketOut = new OutputStreamWriter(socket.getOutputStream())) {
				// Create thread to read incoming messages
				Runnable r = new Runnable() {
					@Override
					public void run() {
						while (true) {
							String msg;
							try {
								msg = socketIn.readLine();
								System.out.println("Received: " + msg);
							} catch (IOException e) {
								break;
							}
							if (msg==null) break; // In case the server closes the socket
						}
					}
				};
				Thread t = new Thread(r);
				t.start();

				// Loop, allowing the user to send messages to the server
				// Note: We still have our scanner
				System.out.println("Enter commands to server or ctrl-D to quit");
				while (in.hasNext()) {
					String line = in.nextLine();
					socketOut.write(line + "\n");
					socketOut.flush();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static boolean validateIpAddress(String ipAddress) {
		boolean formatOK = false;
		// Check for validity (not complete, but not bad)
		String ipPieces[] = ipAddress.split("\\."); // Must escape (see
													// documentation)
		// Must have 4 parts
		if (ipPieces.length == 4) {
			// Each part must be an integer 0 to 255
			formatOK = true; // set to false on the first error
			int byteValue = -1;
			for (String s : ipPieces) {
				byteValue = Integer.parseInt(s); // may throw
													// NumberFormatException
				if (byteValue < 0 | byteValue > 255) formatOK = false;
			}
		}
		return formatOK;
	}

	private static boolean validatePortNumber(String portText) {
		boolean formatOK = false;
		try {
			int portNumber = Integer.parseInt(portText);
			if (portNumber >= 0 & portNumber <= 65535) {
				formatOK = true;
			}
		} catch (NumberFormatException e) {
		}
		return formatOK;
	}
}
