package org.example.file.channel;

import org.example.util.PathUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.READ;

public class FileChannelReadExample {
    public static void main(String[] args) throws IOException {
        Path path = PathUtils.path("file.txt");

        FileChannel fileChannel = FileChannel.open(path, READ);

        ByteBuffer buffer = ByteBuffer.allocate(3);

        String data = "";
        int byteCount;

        while (true) {
            byteCount = fileChannel.read(buffer);
            if (byteCount == -1) {
                break;
            }
            buffer.flip();
            data += StandardCharsets.UTF_8.decode(buffer).toString();
            System.out.println(data);
            buffer.clear();
        }
    }
}
