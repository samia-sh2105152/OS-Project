import java.net.*;
import java.io.*;

public class Client1 {
	public static void main(String args[]) {
		try {
			Socket server = new Socket("localhost", 1300);
			System.out.println("Connected with server " +
					server.getInetAddress() + ":" +
					server.getPort());
			// Set the socket option just in case server stalls
			server.setSoTimeout(2000);
			// Read from the server
			BufferedReader reader =
					new BufferedReader(
							new InputStreamReader(server.getInputStream()));
			// Display result on the screen
			System.out.println("Result = " + reader.readLine());
			// Close the connection
			server.close();
		} catch(IOException ioe) {
			System.out.println("Error" + ioe);
		}
	}
}
