package com.crw.java.list;

/**
 * 通过单向链表实现队列
 * 1. 基本的属性，头节点，尾节点，size
 * 2. 入队、出队方法实现
 *
 * @param <E>
 */
public class MyLinkedQueue<E> {

    private Node<E> head;
    private Node<E> last;
    private int size;

    public MyLinkedQueue() {
        head = new Node<E>(null);
        last = null;
    }

    /**
     * 入队
     *
     * @param e
     */
    void enqueue(E e) {
        Node<E> p = new Node<>(e);
        if (size == 0) {
            head.next = p;
            last = p;
        } else {
            last.next = p;
            last = p;
        }
        size++;
    }

    /**
     * 出队
     *
     * @return
     */
    E dequeue() {
        if (size == 0) { // 空队列
            return null;
        }

        E item = head.next.item;
        head.next = head.next.next;
        size--;
        return item;
    }

    private static class Node<E> {
        E item;

        Node<E> next;

        Node(E x) {
            this(x, null);
        }

        Node(E x, Node<E> next) {
            item = x;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        MyLinkedQueue<Integer> a = new MyLinkedQueue<Integer>();
        a.enqueue(1);
        a.enqueue(2);
        a.enqueue(3);
        a.enqueue(4);
        System.out.println(a.dequeue());
        System.out.println(a.dequeue());
        System.out.println(a.dequeue());
        System.out.println(a.dequeue());
    }
}
