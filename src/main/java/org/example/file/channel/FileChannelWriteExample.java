package org.example.file.channel;

import org.example.util.PathUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

public class FileChannelWriteExample {
    public static void main(String[] args) throws IOException {
        Path path = PathUtils.path("file.txt");
        Files.createDirectories(path.getParent());

        FileChannel fileChannel = FileChannel.open(path, CREATE, WRITE);

        String data = "안녕하세요.";
        Charset charset = StandardCharsets.UTF_8;
        ByteBuffer buffer = charset.encode(data);

        int byteCount = fileChannel.write(buffer);
        System.out.println("file.txt " + byteCount + " bytes.");

        fileChannel.close();
    }
}
