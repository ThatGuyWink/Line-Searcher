package cop2805;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String hostname = "127.0.0.1";
        int port = 1289;

        try (Socket socket = new Socket(hostname, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.print("What line would you like to search (type 'exit' to quit or 'END' to end connection):  ");
                String inputLine = scanner.nextLine(); // Read user input

                if ("exit".equalsIgnoreCase(inputLine)) {
                    out.println(inputLine); // Inform server about termination
                    break; // Exit the loop, ending the program
                }

                out.println(inputLine); // Send the line number to the server

                // Read responses from the server until end marker
                String response;
                while (!(response = in.readLine()).equals("--end--")) {
                    System.out.println(response);
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }
}

