package org.example.socket;

import org.example.socket.nonblocking.Readable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ClientExample {

    public static void main(String[] args) {
        SocketChannel channel = null;
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(true);
            System.out.println("연결요청");
            channel.connect(new InetSocketAddress("localhost", 5001));
            System.out.println("연결성공");

            ByteBuffer buffer = StandardCharsets.UTF_8.encode("Hello, Server");
            channel.write(buffer);
            System.out.println("데이터 보내기 성공");

            buffer = ByteBuffer.allocate(3);
            while (true) {
                int byteCount = channel.read(buffer);
                if (byteCount == -1) {
                    break;
                }
                buffer.flip();
                String message = StandardCharsets.UTF_8.decode(buffer).toString();
                System.out.println("데이터 받기 성공 " + message);
                buffer.clear();
                if (message.equals("하")) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (channel != null && channel.isOpen()) {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
