package org.example.buffer;

import java.nio.ByteBuffer;

public class BufferDoc {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.capacity(); // 전체크기
        buffer.clear(); // position = 0, limit = capacity
        buffer.flip(); // position = 0, limit = position
        buffer.position(); // position 리턴
        buffer.position(1); // set position
        buffer.limit(); // limit 리턴
        buffer.mark(); // 현재 위치를 mark표시
        buffer.remaining(); // position과 limit 사이의 요소 개수
        buffer.reset(); // position을 mark위치로 이동
        buffer.rewind(); // position을 0으로 이동.
    }
}
