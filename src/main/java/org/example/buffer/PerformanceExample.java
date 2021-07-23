package org.example.buffer;

import org.example.util.PathUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

public class PerformanceExample {

    public static long copy10000(FileChannel from, FileChannel to, ByteBuffer buffer) throws IOException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            from.read(buffer);
            buffer.flip();
            to.write(buffer);
            buffer.clear();
        }
        from.position(0);
        return System.currentTimeMillis() - start;
    }

    public static void main(String[] args) throws IOException {
        final String fileName = "jinjjambbong.mp3";
        final Path path = PathUtils.path(fileName);
        final Path to1 = PathUtils.path(fileName + ".1.mp3");
        final Path to2 = PathUtils.path(fileName + ".2.mp3");

        long size = Files.size(path);

        FileChannel from = FileChannel.open(path);
        FileChannel channel1 = FileChannel.open(to1, EnumSet.of(CREATE, WRITE));
        FileChannel channel2 = FileChannel.open(to2, EnumSet.of(CREATE, WRITE));

        ByteBuffer nonDirectBuffer = ByteBuffer.allocate((int) size);
        ByteBuffer directBuffer = ByteBuffer.allocateDirect((int) size);

        copy10000(from, channel1, nonDirectBuffer);
        copy10000(from, channel1, nonDirectBuffer);
        copy10000(from, channel1, nonDirectBuffer);
        copy10000(from, channel1, nonDirectBuffer);
        copy10000(from, channel2, directBuffer);
        copy10000(from, channel2, directBuffer);
        copy10000(from, channel2, directBuffer);
        copy10000(from, channel2, directBuffer);

        System.out.println("논다이렉트 버퍼 : " + copy10000(from, channel1, nonDirectBuffer));
        System.out.println("다이렉트 버퍼 : " + copy10000(from, channel2, directBuffer));

        from.close();
        channel1.close();
        channel2.close();
    }

}
