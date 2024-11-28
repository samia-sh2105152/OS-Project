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
			while(true) {
				Socket nextClient = server.accept();
				System.out.println("Receiving Request From " +
						nextClient.getInetAddress() + ":" +
						nextClient.getPort());
		
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
		
		PrintWriter to_client;
		BufferedReader from_client;
		try {
			to_client = new PrintWriter(nextClient.getOutputStream(), true);
			to_client.println("Connected to server at: " +new Date());
			executeNetworkScript();
			from_client=new BufferedReader(new InputStreamReader(nextClient.getInputStream()));
			String clientRequest = from_client.readLine();
			if ("REQUEST_FILE".equalsIgnoreCase(clientRequest)) {
         		   sendRequestedFile();
        		}else{
			System.out.println("Unknown request from client");
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
private void sendRequestedFile() {
    try {
        File fileToSend = new File("System.sh");
        if (!fileToSend.exists()) {
            System.out.println("system.sh not found.");
            PrintWriter to_client = new PrintWriter(nextClient.getOutputStream(), true);
            to_client.println("Error: Requested file system.sh not found on server.");
            return;
        }

        FileInputStream fileInputStream = new FileInputStream(fileToSend);
        OutputStream clientOutputStream = nextClient.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;

        System.out.println("Sending system.sh to client...");
        while ((bytesRead = fileInputStream.read(buffer)) > 0) {
            clientOutputStream.write(buffer, 0, bytesRead);
        }
        clientOutputStream.flush();
        fileInputStream.close();
        System.out.println("system.sh sent successfully.");
    } catch (Exception e) {
        System.out.println("Error sending system.sh: " + e.getMessage());
    }
}

}


