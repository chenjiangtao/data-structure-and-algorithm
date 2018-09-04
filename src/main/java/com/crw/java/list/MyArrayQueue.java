package com.crw.java.list;

/**
 * 通过数组实现队列
 * 1.循环引用，数组固定大小，环状
 * 2.属性有数组元素，入队索引，出队索引，队列大小size，队列容量。
 *
 * @param <E>
 */
public class MyArrayQueue<E> {

    private E[] items;
    private int size;
    private int takeIndex;
    private int putIndex;
    private int capacity;

    public MyArrayQueue() {
        this(10);
    }

    public MyArrayQueue(int capacity) {
        this.size = 0;
        this.takeIndex = 0;
        this.putIndex = 0;
        this.capacity = capacity;
        items = (E[]) new Object[capacity];
    }

    /**
     * 入队
     *
     * @param x
     */
    void enqueue(E x) {
        items[putIndex] = x; // 插入元素
        if (++putIndex == size) { // 索引满了从头计数
            putIndex = 0;
        }
        size++;
    }

    /**
     * 出队
     *
     * @return
     */
    E dequeue() {
        E x = items[takeIndex];
        items[takeIndex] = null; // 清除数据
        if (++takeIndex == items.length) { // 索引满了从头计数
            takeIndex = 0;
        }
        size--;

        return x;
    }

    public static void main(String[] args) {
        MyArrayQueue<Integer> queue = new MyArrayQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(queue.dequeue());
        }
    }

}
