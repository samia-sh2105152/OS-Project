package client2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client2 {

	public static void main(String[] args) {

		Socket client = null;
		BufferedReader from_server = null;
		PrintWriter to_server = null;

		String serverInput, userInput;
		runSerachScript();

		try {

			client = new Socket("localhost", 1300);
			from_server = new BufferedReader(new InputStreamReader(client.getInputStream()));
			to_server = new PrintWriter(client.getOutputStream());

			System.out.println("Connected with server " + client.getInetAddress() + ":" + client.getPort());

			runSerachScript();
			runClientinfoScript();

			while (true) {
				serverInput = from_server.readLine();
				// if the server receives done just break from this loop
				if (serverInput.equals("done") || serverInput == null) {
					System.out.println(serverInput);
					break;
				}
				// print server input
				System.out.println(serverInput);
				// get user input and send it to the server
				to_server.flush();

			} // end of the loop

		} catch (IOException ioe) {
			System.out.println("Error" + ioe);
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

	private static void runSerachScript() {
		try {
			Process pro = Runtime.getRuntime().exec("./Search.sh");
			BufferedReader search_output = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			String line = search_output.readLine();

			while (line != null) {
				System.out.println(line);
				line = search_output.readLine();
			}
			pro.waitFor(1, TimeUnit.MINUTES);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void runClientinfoScript() {
		// TODO Auto-generated method stub

	}

}
