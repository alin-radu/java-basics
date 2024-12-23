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
    private static volatile boolean keepRunning = true;

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(5001)) {
            System.out.println("Server started, waiting for connection from the client...");

            while (keepRunning) {
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(900_0000);
                connection_id++;
                System.out.println("Server_id_" + connection_id + " connected with the client.");

                executorService.execute(() -> handleClientRequest(socket));
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        } finally {
            keepRunning = false;
            executorService.shutdown();
            System.out.println("Server shut down gracefully.");
        }

        keepRunning = true;
    }

    private static void handleClientRequest(Socket socket) {
        try (
                socket;
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String echoString;
            while (true) {
                System.out.println("Server_id_" + connection_id + " Waiting for a request...");
                echoString = input.readLine();
                System.out.println("Server_id_" + connection_id + " Received a request, the request is: " + echoString);

                if (echoString.equals("exit")) {
                    System.out.println("Connection was terminated.");
                    break;
                }

                output.println("Echo from server: " + echoString);
            }
        } catch (Exception e) {
            System.out.println("Client socket shut down here");
        }

    }
}
