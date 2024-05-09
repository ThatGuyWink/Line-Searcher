Overview

The Line Searcher project is a client-server application that allows users to search for specific lines in a text file hosted on the server. The project consists of three main components:

ClientGUI: A graphical user interface for users to interact with the server.
Client: A command-line client for users who prefer a text-based interface.
Server: A server that processes client requests to search for lines in a text file and returns the results.
Components

ClientGUI
The ClientGUI class provides a graphical user interface for the client. Users can input their search queries, send them to the server, and receive responses in a text area.

Key Features

Text Field: For user input.
Text Area: For displaying server responses.
Search Button: To send search requests to the server.
End Button: To end the connection with the server.
How to Use

Launch the ClientGUI application.
Enter the server address and port.
Input the line number you want to search for in the text field.
Click the "Search" button to send the request.
View the response in the text area.
Click the "End" button to terminate the connection.
Client
The Client class provides a command-line interface for the client. Users can input their search queries directly in the console.

Key Features

Interactive Console: Prompts users for input.
Search Functionality: Sends line number requests to the server.
Exit Command: Allows users to exit the application.
How to Use

Run the Client application.
Enter the line number you want to search for.
View the response in the console.
Type "exit" to terminate the application.
Type "END" to end the server connection.
Server
The Server class handles client connections and processes search requests. It reads a text file and returns the specified lines to the client.

Key Features

Client Handling: Accepts multiple client connections.
Search Functionality: Reads lines from a text file and sends them to the client.
Shutdown Command: Allows the server to be shut down gracefully.
How to Use

Run the Server application.
The server listens for client connections on the specified port.
When a client connects, the server processes search requests.
The server can be shut down by sending the "END" command from a client.
