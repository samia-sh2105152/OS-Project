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
	private static List<String> clientRequests = new ArrayList<>();
	public static void main(String args[]) {
			
		try {
			ServerSocket server = new ServerSocket(1300);
			System.out.println("Server waiting for client on port " +
					server.getLocalPort());
			System.out.println("Service Started");
			while(true) {
				Socket nextClient = server.accept();
				System.out.println("Receiving Request From " +
						nextClient.getInetAddress() + ":" +
						nextClient.getPort());
		
				
				Service threadService = new Service(nextClient, clientRequests);
				threadService.start();
			}
       	
		} catch(IOException ioe){
			System.out.println("Error" + ioe);
		}
	}
}

 class Service extends Thread{
	Socket nextClient;
	private List<String> clientRequests;

	public Service(Socket nextClient, List<String> clientRequests) {
        this.nextClient = nextClient;
        this.clientRequests = clientRequests;
    	}
	
	public void run() {
		
		PrintWriter to_client;
		BufferedReader from_client;
		try {
			to_client = new PrintWriter(nextClient.getOutputStream(), true);
			to_client.println("Connected to server at: " +new Date());
			executeNetworkScript();
			from_client=new BufferedReader(new InputStreamReader(nextClient.getInputStream()));
			to_client.println("Do you want system information? (yes/no)");
			String clientResponse = from_client.readLine();

			if (clientResponse != null && clientResponse.equalsIgnoreCase("yes")) {
                System.out.println("Client requested system information.");
                sendSystemInfo(to_client); // Execute system.sh and send output
            } else {
                System.out.println("Client declined system information.");
                to_client.println("No system information will be provided.");
            }
			if(nextClient !=null)
				nextClient.close();
		} catch(IOException ioe){
			System.out.println("Error" + ioe);
		}
		
	}
	private void executeNetworkScript() {
        try {
          
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "./Network.sh");
            processBuilder.redirectErrorStream(true);

            
            Process process = processBuilder.start();

       
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println("Output from Network.sh:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

         
            int exitCode = process.waitFor();
            System.out.println("Network.sh executed with exit code: " + exitCode);
        } catch (Exception e) {
            System.out.println("Error executing Network.sh: " + e.getMessage());
        }
    }
private void sendSystemInfo(PrintWriter to_client) {
    try {
         ProcessBuilder processBuilder = new ProcessBuilder("bash", "./System.sh");
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.out.println("system.sh execution failed with exit code: " + exitCode);
        } else {
            System.out.println("system.sh executed successfully.");
        }

        //send output
        to_client = new PrintWriter(nextClient.getOutputStream(), true);
        to_client.println("System Information:");
        to_client.println(output.toString()); 
        to_client.println("--- End of System Info ---");
        System.out.println("System information sent to client.");
    } catch (Exception e) {
        System.out.println("Error sending system.sh: " + e.getMessage());
    }
}

}


