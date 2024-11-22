import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class Service extends Thread{
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
            // Replace "path/to/Network.sh" with the actual path of your script
            String scriptPath = "path/to/Network.sh";
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "../scripts/Network.sh");
            processBuilder.environment().put("TERM", "xterm");
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
