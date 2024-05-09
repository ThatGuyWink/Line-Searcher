package cop2805;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.List;

public class Server {
 
	private static boolean handleClient(Socket socket) {
	    try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	         PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

	        String textLine;
	        while ((textLine = input.readLine()) != null) {
	            if ("exit".equalsIgnoreCase(textLine.trim())) {
	                output.println("Disconnecting...");
	                break;  // Disconnect this client
	            }
	            if ("END".equalsIgnoreCase(textLine.trim())) {
	                output.println("Shutting down server...");
	                return false;  // Signal to shutdown the server
	            }

	            try {
	                int lineNumber = Integer.parseInt(textLine.trim());
	                LineSearcher searcher = new LineSearcher("hamlet.txt");
	                List<String> lines = searcher.findLines(lineNumber);
	                for (String line : lines) {
	                    output.println(line);
	                }
	                output.println("--end--"); // Signal end of response
	            } catch (NumberFormatException e) {
	                output.println("Error: Invalid number format");
	                output.println("--end--");
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error handling client: " + e.getMessage());
	    }
	    return true;  // Keep running the server unless the END command was received
	}
	
	public static void main(String[] args) {
	    int port = 1289;
	    boolean running = true;  // Server control flag

	    try (ServerSocket serverSocket = new ServerSocket(port)) {
	        System.out.println("Server is listening on port " + port);

	        while (running) {  // Use the flag to control the loop
	            try (Socket socket = serverSocket.accept()) {
	                System.out.println("New client connected");
	                running = handleClient(socket);  // Handle the client and decide if continuing
	            } catch (IOException e) {
	                System.out.println("Exception in client connection: " + e.getMessage());
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Server exception: " + e.getMessage());
	    }
	    System.out.println("Server shutdown.");
	}

	

}


