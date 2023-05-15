package com.jeff_media.lunatic.data;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a queue-like buffer that holds a fixed amount of items.
 */
public class BufferQueue<T> {

    private final int size;
    private final T[] buffer;
    private int index = 0;

    /**
     * Create a new BufferQueue instance
     * @param size Size of the buffer
     */
    public BufferQueue(int size) {
        this.size = size;
        this.buffer = (T[]) new Object[size];
    }

    /**
     * Add an item at the top of the buffer
     * @param item Item to add
     */
    public void add(@Nullable T item) {
        buffer[index] = item;
        index = (index + 1) % size;
    }

    /**
     * Get the item at the bottom of the buffer
     * @return Item at the specified index
     */
    public @Nullable T get() {
        return buffer[index];
    }

}
