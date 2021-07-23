package org.example.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ServerExample {

    public static void main(String[] args) {
        ServerSocketChannel server = null;
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(true);
            server.bind(new InetSocketAddress(5001));

            while (true) {
                System.out.println("연결 기다림");
                SocketChannel channel = server.accept();
                System.out.println("연결 수락함. " + channel.getRemoteAddress().toString());

                ByteBuffer buffer = ByteBuffer.allocate(100);
                int read = channel.read(buffer);
                buffer.flip();
                String message = StandardCharsets.UTF_8.decode(buffer).toString();
                System.out.println("데이터 받기 성공 " + message);

                buffer = StandardCharsets.UTF_8.encode("가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하");
                channel.write(buffer);
                System.out.println("데이터 보내기 성공");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (server != null && server.isOpen()) {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
