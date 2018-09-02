package com.crw.java.adt;

import java.util.*;

/**
 * 设计,提供三个类:
 * 1. MyLinkedList本身，包含两端的链，表的大小和一些方法
 * 2. Node类，包含数据和指向前后的链
 * 3. LinkedListIterator类 提供next,hasNext,remove方法
 *
 * @param <E>
 */
public class MyLinkedList<E> implements List<E>, Iterable<E> {

    /**
     * 大小
     */
    private int size;
    /**
     * 修改次数
     */
    private int modCount = 0;
    /**
     * 头节点
     */
    private Node<E> first;
    /**
     * 尾节点
     */
    private Node<E> last;

    /**
     * 节点，双向
     *
     * @param <E>
     */
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * 添加元素到头节点
     *
     * @param e
     */
    private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
        modCount++;
    }

    /**
     * 添加元素到尾节点
     *
     * @param e
     */
    void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        modCount++;
    }

    /**
     * 插入一个元素在一个非空的节点之前。修改四条链。
     */
    void linkBefore(E e, Node<E> succ) {
        final Node<E> oldPrev = succ.prev; // 旧的前节点
        final Node<E> newNode = new Node<>(oldPrev, e, succ); // 新节点。修改了两条链。
        succ.prev = newNode; // 修改后节点的前链指向新节点。修改了一条链
        if (oldPrev == null) // 如果succ是头结点，则修改头节点为新节点。修改了一条链。
            first = newNode;
        else // 如果succ是不头结点，旧的前节点的后节点指向新节点。修改了一条链。
            succ.next = newNode;
        size++;
        this.modCount++;
    }

    /**
     * 获取索引处节点.链表只能通过遍历查找第index处的节点
     *
     * @param index
     * @return
     */
    Node<E> node(int index) {
        if (index < 0 || index > size()) {
            throw new NoSuchElementException();
        }
        Node<E> p = null;
        if (index < size() / 2) { // 索引位置小于一半，从前往后遍历查找
            p = first;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
        } else { // 索引位置大于一半，从后往前遍历查找
            p = last;
            for (int i = size - 1; i > index; i--) {
                p = p.prev;
            }
        }

        return p;
    }

    /**
     * 删除一个非空节点
     *
     * @param x
     * @return
     */
    E unlink(Node<E> x) {
        E element = x.item;
        Node<E> prev = x.prev;
        Node<E> next = x.next;

        // 改链
        if (prev == null) { // x是头节点
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) { // x是尾节点
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        modCount++;
        return element;
    }

    public MyLinkedList() {
        clear();
    }

    @Override
    public E get(int index) {
        return node(index).item;
    }

    @Override
    public boolean add(E e) {
        add(size() - 1, e);
        return true;
    }

    @Override
    public void add(int index, E element) {
        linkBefore(element, node(index));
    }

    @Override
    public E set(int index, E element) {
        Node<E> x = node(index);
        E oldVal = x.item;
        x.item = element;
        return oldVal;
    }

    /**
     * 删除元素o,从头遍历
     *
     * @param o
     * @return
     */
    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public E remove(int index) {
        return unlink(node(index));
    }

    @Override
    public void clear() {
        first = new Node<>(null, null, null);
        last = new Node<>(first, null, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }


    /**
     * 迭代器
     * 1. 提供一个当前节点
     * 2. 当当前节点next是last的时候，对next的调用时非法的
     * 3.
     */
    private class LinkedListIterator implements ListIterator<E> {
        /**
         * 表示调用next()返回的节点
         */
        private Node<E> next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        @Override
        public boolean hasNext() {
            return next != last;
        }

        @Override
        public E next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E data = this.next.item;
            this.next = this.next.next; // 修改链
            okToRemove = true; // 可以删除
            return data;
        }

        @Override
        public boolean hasPrevious() {
            return next.prev != first;
        }

        @Override
        public E previous() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            E data = this.next.item;
            this.next = this.next.prev;// 修改链
            okToRemove = true;
            return data;
        }

        @Override
        public void remove() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }
            MyLinkedList.this.remove(next.prev);
            okToRemove = false;
            expectedModCount++;
        }

        @Override
        public void set(E e) {
            next.item = e;
        }

        @Override
        public void add(E e) {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            MyLinkedList.this.linkBefore(e, next.next);
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }


    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new LinkedListIterator();
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }


    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }


    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (; ; ) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }

    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        ListIterator<Integer> iter = list.listIterator();
        while (iter.hasNext()) {
            if (iter.nextIndex() % 2 == 0) {
                iter.set(100);
            }
            System.out.println(iter.next());
        }
        System.out.println("iter end");

        System.out.println("indexOf:" + list.indexOf(100));
        System.out.println("lastIndexOf:" + list.lastIndexOf(100));

        System.out.println("remove:" + list.remove(2));

        list.add(66);
        System.out.println(list);
        list.set(4, 67);
        System.out.println(list);
    }
}
