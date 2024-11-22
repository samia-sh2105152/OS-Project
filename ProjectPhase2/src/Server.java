import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	public static void main(String args[]) {
		try {
			ServerSocket server = new ServerSocket(1300);
			System.out.println("Server waiting for client on port " +
					server.getLocalPort());
			System.out.println("Service Started");
			for(;;) {
				Socket nextClient = server.accept();
				System.out.println("Receiving Request From " +
						nextClient.getInetAddress() + ":" +
						nextClient.getPort());
				PrintWriter output =
						new PrintWriter(nextClient.getOutputStream(), true);
				output.println(new Date());
				Service threadService=new Service(nextClient);
				threadService.start();
			}
		} catch(IOException ioe){
			System.out.println("Error" + ioe);
		}
	}
}
