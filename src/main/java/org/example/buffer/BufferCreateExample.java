package org.example.buffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class BufferCreateExample {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[10]); // 논다이렉트
        CharBuffer charBuffer = CharBuffer.wrap(new char[10]);
        ByteBuffer wrap = ByteBuffer.wrap(new byte[10], 0, 5); // 배열에 특정 구간만 사용
        CharBuffer stringBuffer = CharBuffer.wrap("스트링으로도 만들수 있다.. 값이 바로 들어감.");
        System.out.println(stringBuffer);
    }

}
