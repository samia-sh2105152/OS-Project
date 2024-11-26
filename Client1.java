import java.net.*;
import java.io.*;

public class Client1 {
	public static void main(String args[]) {
		PrintWriter toServer = null;
		try {
			Socket server = new Socket("192.168.64.3", 1300);
			System.out.println("Connected with server " +
					server.getInetAddress() + ":" +
					server.getPort());
			// Set the socket option just in case server stalls
			server.setSoTimeout(5000);
			// Read from the server
			BufferedReader reader =
					new BufferedReader(
							new InputStreamReader(server.getInputStream()));
			// Display result on the screen
			System.out.println("Today's date:  " + reader.readLine());
			toServer = new PrintWriter(server.getOutputStream(), true);
			 toServer.println("REQUEST_FILE"); // Signal to request system.sh
            System.out.println("Request sent to server: REQUEST_FILE");

            // Receive the file from the server
            InputStream serverInputStream = server.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream("received_system.sh");
            byte[] buffer = new byte[4096];
            int bytesRead;

            System.out.println("Receiving system.sh from server...");
            while ((bytesRead = serverInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.close();
            System.out.println("File received successfully as received_system.sh");

            // Display the content of the received file
            BufferedReader fileReader = new BufferedReader(new FileReader("received_system.sh"));
            String line;
            System.out.println("Content of received_system.sh:");
            while ((line = fileReader.readLine()) != null) {
                System.out.println(line);
            }
            fileReader.close();


			// Close the connection
			server.close();
		} catch(IOException ioe) {
			System.out.println("Error" + ioe);
		}
	}
}
