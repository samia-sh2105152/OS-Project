package client1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client1 {

	public static void main(String[] args) throws IOException {
		// runLoginScript("127.0.0.1");
		runClient();
		//runCheckScript();
	}
	public static void runClient() throws IOException {
		
		Socket client = null;
		BufferedReader from_server = null;
		PrintWriter to_server = null;

		String serverInput;

		try {
			System.out.println("What is Server ip ");
			Scanner input = new Scanner(System.in);
			String ip = input.nextLine();

			client = new Socket(ip, 1300);
			from_server = new BufferedReader(new InputStreamReader(client.getInputStream()));
			to_server = new PrintWriter(client.getOutputStream());

			System.out.println("Client: Connected with server " + client.getInetAddress() + ":" + client.getPort());

			// runLoginScript(ip);
			// runCheckScript(ip);
			

			while (true) {
				to_server.println("System info");
				to_server.flush();
				serverInput = from_server.readLine();
				
				// if the server receives done just break from this loop
				while (!(serverInput.equals("--- End of System Info ---") || serverInput == null)){
					System.out.println(serverInput);
					serverInput = from_server.readLine();
					
				}
				//wating for 5 min
				TimeUnit.SECONDS.sleep(5);
				
				
				 
				

			} // end of the loop

		} catch (IOException ioe) {
			System.out.println("Error" + ioe);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (from_server != null)
					from_server.close();
				if (client != null)
					client.close();
				if (to_server != null)
					to_server.close();
			} catch (IOException ioee) {
				System.err.println(ioee);
			}
		}

	}

	private static void runLoginScript(String ip) {
		try {
			ProcessBuilder pbuilder = new ProcessBuilder("./scripts/login.sh",ip);
			pbuilder.inheritIO();

			Process pro = pbuilder.start();
			BufferedReader search_output = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			String line = search_output.readLine();

			while (line != null) {
				System.out.println(line);
				line = search_output.readLine();
			}
			pro.waitFor(1, TimeUnit.MINUTES);
//			//Process bigFilePro = Runtime.getRuntime().exec("cat bigfile.txt " );
//			BufferedReader bigFile_output = new BufferedReader(new InputStreamReader(bigFilePro.getInputStream()));
//			String line2 = bigFile_output.readLine();
//
//			while (line2 != null) {
//				System.out.println(line2);
//				line2 = bigFile_output.readLine();
//			}
			
			

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void runCheckScript(String ip) {
		try {
			ProcessBuilder pb = new ProcessBuilder("scripts/Check.sh");
			pb.redirectErrorStream(true);
			
			//Process pro = Runtime.getRuntime().exec("scripts/Clientinfo.sh");
			Process pro = pb.start();
			BufferedReader search_output = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			String line = search_output.readLine();

			while (line != null) {
				System.out.println(line);
				line = search_output.readLine();
			}
			pro.waitFor(10, TimeUnit.SECONDS);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
	}

}
