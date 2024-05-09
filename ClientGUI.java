package cop2805;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClientGUI extends JFrame {
    private JTextField textField;
    private JTextArea textArea;
    private JButton sendButton, endButton;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public ClientGUI(String serverAddress, int port) {
        
        setTitle("Line Searcher");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // JText field for input
        textField = new JTextField();
        add(textField, BorderLayout.NORTH);

        // JText area for responses
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        sendButton = new JButton("Search");
        buttonPanel.add(sendButton);

        // Button to end the server - client connection
        endButton = new JButton("End");
        buttonPanel.add(endButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event handling for the Search button
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendRequest();
            }
        });

        // Event handling for the End Server button
        endButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                endServer();
            }
        });

        // Sets up connections
        serverConnection(serverAddress, port);

        // Make the frame visible
        setVisible(true);
    }

    private void serverConnection(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            textArea.append("Connected to server at " + serverAddress + ":" + port + "\n");
        } catch (Exception e) {
            textArea.append("Unable to connect to server: " + e.getMessage() + "\n");
            System.exit(-1); // Exit if cannot connect
        }
    }

    private void sendRequest() {
        String line = textField.getText();
        if (!line.isEmpty()) {
            out.println(line);
            textField.setText("");
            readResponse();
        }
    }

    private void readResponse() {
        try {
            String response;
            while (!(response = in.readLine()).equals("--end--")) {
                textArea.append(response + "\n");
            }
            textArea.append("\n-- End of response --\n\n");
        } catch (IOException e) {
            textArea.append("Error reading from server: " + e.getMessage() + "\n");
        }
    }

    private void endServer() {
        out.println("END");
        textArea.append("Sent shutdown command to server.\n");
        try {
            Thread.sleep(500);  // Wait for the server to shutdown
            socket.close();
            out.close();
            in.close();
        } catch (Exception e) {
            textArea.append("Error during server shutdown: " + e.getMessage() + "\n");
        }
        System.exit(0);  // Close the client application
    }

    public static void main(String[] args) {
        new ClientGUI("127.0.0.1", 1289);
    }
}
