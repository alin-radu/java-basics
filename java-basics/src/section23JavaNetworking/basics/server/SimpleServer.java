package section23JavaNetworking.basics.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@SuppressWarnings({"DuplicatedCode"})

public class SimpleServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5001)) {
            System.out.println("Server started, waiting for connection from the client...");

            try (
                    Socket socket = serverSocket.accept();
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)
            ) {
                System.out.println("Server connected with the client.");

                String echoString;
                while (true) {
                    System.out.println("before input.readLine()");
                    echoString = input.readLine(); // will block were waiting for a new line,  get client request
                    System.out.println("after input.readLine()");

                    System.out.println("Server got request data: " + echoString);

                    if (echoString.equals("exit")) {
                        System.out.println("Connection was terminated.");
                        break;
                    } else {
                        output.println("Echo from server... " + echoString);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        } finally {
            System.out.println("Server Disconnected.");
        }
    }
}
