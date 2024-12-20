package section23JavaNetworking.basics.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5001)) {

            try (
                    Socket socket = serverSocket.accept();
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)
            ) {
                System.out.println("Server accepts client connection.");

                String echoString;
                while (true) {
                    echoString = input.readLine();

                    System.out.println("Server got request data: " + echoString);

                    if (echoString.equals("exit")) {
                        System.out.println("Connection will terminate");
                        break;
                    }

                    output.println("Echo from server: " + echoString);
                }
            }

        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
