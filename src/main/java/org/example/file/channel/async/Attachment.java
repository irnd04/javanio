package org.example.file.channel.async;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;

public class Attachment {
    final Path path;
    final AsynchronousFileChannel fileChannel;
    ByteBuffer byteBuffer;

    public Attachment(Path path, AsynchronousFileChannel fileChannel) {
        this.path = path;
        this.fileChannel = fileChannel;
    }

    public Attachment(Path path, AsynchronousFileChannel fileChannel, ByteBuffer buffer) {
        this.path = path;
        this.fileChannel = fileChannel;
        this.byteBuffer = buffer;
    }
}
