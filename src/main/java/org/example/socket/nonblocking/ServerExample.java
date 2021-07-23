package org.example.socket.nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerExample {

    static List<SocketChannel> socketChannels = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(5001));
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, serverSocketChannel.validOps());

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            for (SocketChannel channel : socketChannels) {
                try {
                    ByteBuffer buffer = StandardCharsets.UTF_8.encode("Hello, Client");
                    channel.register(selector, SelectionKey.OP_WRITE, buffer);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5, TimeUnit.SECONDS);

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    socketChannels.add(socketChannel);
                    System.out.println("연결 수락 " + socketChannel.getRemoteAddress());
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    try {
                        ByteBuffer buffer = ByteBuffer.allocate(256);
                        int b = socketChannel.read(buffer);
                        if (b == -1) {
                            socketChannels.remove(socketChannel);
                            socketChannel.close();
                            continue;
                        }
                        buffer.flip();
                        System.out.println(socketChannel.getRemoteAddress() + " : " + StandardCharsets.UTF_8.decode(buffer));
                    } catch (Exception e) {
                        e.printStackTrace();
                        socketChannels.remove(socketChannel);
                        socketChannel.close();
                    }
                } else if (selectionKey.isWritable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    try {
                        selectionKey.interestOps();
                        System.out.println("notify " + socketChannel.getRemoteAddress());
                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                        int write = socketChannel.write(buffer);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } catch (Exception e) {
                        e.printStackTrace();
                        socketChannels.remove(socketChannel);
                        socketChannel.close();
                    }

                }
                iterator.remove();
            }
        }

    }

}
