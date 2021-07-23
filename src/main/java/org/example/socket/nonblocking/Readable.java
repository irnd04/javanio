package org.example.socket.nonblocking;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Readable implements Runnable {

    private final SocketChannel channel;

    public Readable(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(128);
        try {
            while (true) {
                int byteCount = channel.read(buffer);
                if (byteCount == -1) {
                    break;
                }
                buffer.flip();
                String message = StandardCharsets.UTF_8.decode(buffer).toString();
                System.out.println("데이터 받기 성공 " + message);
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }
}
