package org.example.buffer;

import java.nio.ByteBuffer;

public class BufferSizeExample {

    public static void main(String[] args) {
        final int size = 2000 * 1024 * 1024;
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(size); // 운영체제가 관리하는 메모리에 생성
        System.out.println("다이렉트 버퍼 생성 성공");
        ByteBuffer nonDirectBuffer = ByteBuffer.allocate(size); // JVM 메모리(힙)에 생성
        System.out.println("논다이렉트 버퍼 생성 성공");
    }

}
