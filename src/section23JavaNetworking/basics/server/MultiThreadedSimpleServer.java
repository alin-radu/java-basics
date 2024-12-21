package section23JavaNetworking.basics.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings({"DuplicatedCode"})

public class MultiThreadedSimpleServer {
    private static int connection_id = 0;

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        System.out.println("before try");

        try (ServerSocket serverSocket = new ServerSocket(5001)) {

            while (true) {

                Socket socket = serverSocket.accept();
                connection_id++;

                System.out.println("Server_id " + connection_id + " accepts client connection");
                socket.setSoTimeout(90_0000);

                executorService.submit(() -> handleClientRequest(socket));
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }

    private static void handleClientRequest(Socket socket) {
        try (
                socket;
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String echoString;
            while (true) {
                System.out.println("before readLine");
                echoString = input.readLine();
                System.out.println("after redLine");

                System.out.println("Server got request data: " + echoString);

                if (echoString.equals("exit")) {
                    System.out.println("Connection was terminated.");
                    break;
                } else {
                    output.println("Echo from server: " + echoString);
                }
            }
        } catch (Exception e) {
            System.out.println("Client socket shut down here");
        }

    }
}
