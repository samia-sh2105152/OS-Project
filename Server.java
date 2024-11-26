import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
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

 class Service extends Thread{
	Socket nextClient;

	public Service(Socket nextClient) {
		this.nextClient = nextClient;
	}
	
	public void run() {
		
		PrintWriter output;
		try {
			output = new PrintWriter(nextClient.getOutputStream(), true);
			output.println("Connected to server at: " +new Date());
			executeNetworkScript();
			
			if(nextClient !=null)
				nextClient.close();
		} catch(IOException ioe){
			System.out.println("Error" + ioe);
		}
		
	}
	private void executeNetworkScript() {
        try {
          
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "../Network.sh");
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

       
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println("Output from Network.sh:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            System.out.println("Network.sh executed with exit code: " + exitCode);
        } catch (Exception e) {
            System.out.println("Error executing Network.sh: " + e.getMessage());
        }
    }
}


