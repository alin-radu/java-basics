package section23JavaNetworking.basics.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) {

        try (
                Socket socket = new Socket("localhost", 5001);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        ) {
            Scanner scanner = new Scanner(System.in);

            String requestString;
            String responseString;
            do {
                System.out.println("Enter string to be echoed (sent to server): ");
                requestString = scanner.nextLine();

                output.println(requestString); // sends the request string to the server

                if (!requestString.equals("exit")) {
                    System.out.println("before input.readLine()");
                    responseString = input.readLine(); // will block were waiting for a new line, get server response
                    System.out.println("after input.readLine()");

                    System.out.println(responseString);
                }
            } while (!requestString.equals("exit"));

        } catch (IOException e) {
            System.out.println("Client Error: " + e.getMessage());
        } finally {
            System.out.println("Client Disconnected.");
        }
    }
}
