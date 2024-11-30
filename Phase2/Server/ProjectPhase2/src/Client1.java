import java.net.*;
import java.io.*;

public class Client1 {
	public static void main(String args[]) {
		PrintWriter toServer;
		BufferedReader fromServer;
		try {
			Socket server = new Socket("192.168.64.3", 1300);
			System.out.println("Connected with server " +
					server.getInetAddress() + ":" +
					server.getPort());
			// Set the socket option just in case server stalls
			server.setSoTimeout(5000);
			// Read from the server
			fromServer =new BufferedReader(new InputStreamReader(server.getInputStream()));
			toServer = new PrintWriter(server.getOutputStream(), true);
			String serverMessage1 = fromServer.readLine();
            		System.out.println("Server: " + serverMessage1);
			String serverMessage2 = fromServer.readLine();
                        System.out.println("Server: " + serverMessage2);



            // recieving file
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Your response (yes/no)?: ");
            String response = userInput.readLine();
            toServer.println(response);

            // If the server sends system information, display it
            if (response.equalsIgnoreCase("yes")) {
                String serverResponse;
                System.out.println("System Information:");
                while ((serverResponse = fromServer.readLine()) != null) {
                    System.out.println(serverResponse);
                }
            }


			// Close the connection
			server.close();
		} catch(IOException ioe) {
			System.out.println("Error" + ioe);
		}
	}
}
