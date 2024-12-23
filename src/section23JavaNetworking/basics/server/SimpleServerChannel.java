package section23JavaNetworking.basics.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class SimpleServerChannel {
    public static void main(String[] args) {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {

            serverChannel.socket().bind(new InetSocketAddress(5001));
            System.out.println("Server is listening on port " + serverChannel.socket().getLocalPort() + " ...");

            // create connection
            while (true) {
                SocketChannel clientChannel = serverChannel.accept();
                System.out.printf("Client %s connected%n", clientChannel.socket().getRemoteSocketAddress());

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                SocketChannel channel = clientChannel;

                int readBytes = channel.read(buffer);

                if (readBytes > 0) {
                    buffer.flip();

                    channel.write(ByteBuffer.wrap("Echo from the server: ".getBytes(StandardCharsets.UTF_8)));

                    while (buffer.hasRemaining()) {
                        channel.write(buffer);
                    }

                    buffer.clear();
                    System.out.println("Response was send to the " + channel.socket().getRemoteSocketAddress());
                } else if (readBytes == -1) {
                    System.out.printf("Connection to %s lost%n", channel.socket().getRemoteSocketAddress());
                    channel.close();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
