package section23JavaNetworking.basics.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SimpleServerChannel {
    public static void main(String[] args) {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {

            serverChannel.socket().bind(new InetSocketAddress(5001));
            serverChannel.configureBlocking(false);
            System.out.println("Server is listening on port " + serverChannel.socket().getLocalPort() + "...");

            List<SocketChannel> clientChannels = new ArrayList<>();
            while (true) {
                SocketChannel clientChannel = serverChannel.accept();

                if (clientChannel != null) {
                    clientChannel.configureBlocking(false);
                    clientChannels.add(clientChannel);
                    System.out.printf("Client %s connected%n", clientChannel.socket().getRemoteSocketAddress());
                }

                ByteBuffer buffer = ByteBuffer.allocate(1024);

                for (int i = 0; i < clientChannels.size(); i++) {

                    SocketChannel channel = clientChannels.get(i);

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
                        channel.close();
                        clientChannels.remove(i);
                        System.out.printf("Connection to %s lost%n", channel.socket().getRemoteSocketAddress());
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
