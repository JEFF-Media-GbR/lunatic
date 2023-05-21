package com.jeff_media.lunatic.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BufferQueueTest {

    @Test
    public void testBuffer() {
        BufferQueue<Integer> buffer = new BufferQueue<>(5);
        Assertions.assertNull(buffer.get());
        buffer.add(1);
        Assertions.assertNull(buffer.get());
        buffer.add(2);
        Assertions.assertNull(buffer.get());
        buffer.add(3);
        Assertions.assertNull(buffer.get());
        buffer.add(4);
        Assertions.assertNull(buffer.get());
        buffer.add(5);
        Assertions.assertEquals(1, buffer.get());
        buffer.add(6);
        Assertions.assertEquals(2, buffer.get());

    }
}
