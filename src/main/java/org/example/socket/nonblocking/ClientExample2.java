package org.example.socket.nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ClientExample2 {

    public static void main(String[] args) {
        SocketChannel channel = null;
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(true);
            System.out.println("연결요청");
            channel.connect(new InetSocketAddress("localhost", 5001));
            System.out.println("연결성공");

            new Thread(new Readable(channel)).start();

            for (int i = 0; i < 10; i++) {
                String s = "Hi, Server";
                ByteBuffer buffer = StandardCharsets.UTF_8.encode(s);
                channel.write(buffer);
                System.out.println(s + " 데이터 보내기 성공");
                Thread.sleep(2000);
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
