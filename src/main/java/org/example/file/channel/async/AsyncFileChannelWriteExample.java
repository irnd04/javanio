package org.example.file.channel.async;

import org.example.util.PathUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncFileChannelWriteExample {

    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 10; i++) {
            Path path = PathUtils.path(String.format("async.%d.txt", i));
            Files.createDirectories(path.getParent());
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
                    path, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE), executorService);
            ByteBuffer buffer = StandardCharsets.UTF_8.encode("안녕하세요.. 하이요.");
            Attachment attachment = new Attachment(path, fileChannel);
            fileChannel.write(buffer, 0, attachment, new CompletionHandler<Integer, Attachment>() {
                @Override
                public void completed(Integer result, Attachment attachment) {
                    String s = String.format("%s %s", Thread.currentThread().getName(), result);
                    System.out.println(s);
                    try {
                        fileChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Throwable exc, Attachment attachment) {
                    exc.printStackTrace();
                    try {
                        fileChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        executorService.awaitTermination(3, TimeUnit.SECONDS);
        executorService.shutdown();

    }

}
