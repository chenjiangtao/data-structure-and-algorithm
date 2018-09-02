package com.crw.java.adt;

import java.util.*;

/**
 * 基本的列表，此处变量尽量与java.util.ArrayList一致。
 *
 * @param <T>
 */
public class MyArrayList<T> implements List<T>, Iterable<T> {


    /**
     * 数据
     */
    private T[] elementData;

    /**
     * 大小
     */
    private int size;

    /**
     * 默认的空数据
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 初始化一个空列表
     */
    public MyArrayList() {
        size = 0;
        elementData = (T[]) EMPTY_ELEMENTDATA;
    }

    /**
     * 返回列表大小
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 判断列表是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }


    /**
     * 清空列表
     */
    public void clear() {
        for (int i = 0; i < size; i++)
            elementData[i] = null;
        size = 0;
    }

    /**
     * 消减size至当前容器数据大小
     */
    public void trimToSize() {
        if (size < elementData.length) {
            ensureCapacity(size);
        }
    }

    /**
     * 扩容
     *
     * @param newCapacity
     */
    public void ensureCapacity(int newCapacity) {
        if (newCapacity < size) {
            return;
        }

        // 拷贝元素到新数组
        T[] old = elementData;
        elementData = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            elementData[i] = old[i];
        }
    }

    /**
     * 获取索引处元素
     *
     * @param index
     * @return
     */
    public T get(int index) {
        return elementData[index];
    }

    /**
     * 设置索引处元素并返回旧值
     *
     * @param index
     * @param element
     * @return
     */
    public T set(int index, T element) {
        if (index < 0 || index >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        T oldVal = elementData[index];
        elementData[index] = element;
        return oldVal;
    }

    /**
     * 添加索引处元素
     *
     * @param index
     * @param element
     */
    @Override
    public void add(int index, T element) {
        if (index == size()) {
            ensureCapacity(size() * 2 + 1); // 扩容。+1是用于size == 0的情况。
        }
        // 从数组尾部开始，数据后移一个位置直到索引处
        for (int i = size; i > index; i--) {
            elementData[i] = elementData[i - 1];
        }
        elementData[index] = element;
        size++;
    }

    /**
     * 尾处添加一个元素
     *
     * @param t
     * @return
     */
    @Override
    public boolean add(T t) {
        add(size(), t);
        return true;
    }

    /**
     * 删除索引处元素
     *
     * @param index
     * @return
     */
    @Override
    public T remove(int index) {
        T removeVal = elementData[index];
        // 从索引处开始，后移位置数据前移一个位置
        for (int i = index; i < size - 1; i++) {
            elementData[i] = elementData[i + 1];
        }
        size--;
        return removeVal;
    }

    /**
     * 删除某个元素
     *
     * @param o
     * @return
     */
    @Override
    public boolean remove(Object o) {
        // 遍历元素找到相同的元素，删除。
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    remove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    remove(index);
                    return true;
                }
        }
        return false;
    }

    /**
     * 某个元素顺序遍历的索引值
     *
     * @param o
     * @return
     */
    @Override
    public int indexOf(Object o) {
        // 从头遍历元素，找到与o相同的值，返回索引
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i] == null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    /**
     * 某个元素倒序遍历的索引值
     *
     * @param o
     * @return
     */
    @Override
    public int lastIndexOf(Object o) {
        // 从尾巴遍历元素，找到与o相同的值，返回索引
        if (o == null) {
            for (int i = size - 1; i >= 0; i--)
                if (elementData[i] == null)
                    return i;
        } else {
            for (int i = size - 1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ArrayListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ArrayListIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    /**
     * 复制一个数组返回
     *
     * @return
     */
    @Override
    public Object[] toArray() {
        T[] datas = (T[]) new Object[size()];
        for (int i = 0; i < size; i++) {
            datas[i] = elementData[i];
        }
        return datas;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Iterator<? extends T> iter = iterator();
        while (iter.hasNext()) {
            add(iter.next());
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
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

    private class ArrayListIterator implements ListIterator<T> {
        /**
         * 下个元素的索引
         */
        private int cursor;
        /**
         * 上个元素的索引返回
         */
        private int lastRet = -1;

        public ArrayListIterator() {
            cursor = 0;
            lastRet = -1;
        }

        public ArrayListIterator(int index) {
            cursor = index;
            lastRet = index - 1;
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public boolean hasNext() {
            return cursor < size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastRet = cursor;
            return elementData[cursor++];
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public boolean hasPrevious() {
            return cursor >= 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            lastRet = cursor;
            return elementData[--cursor];
        }


        @Override
        public void remove() {
            cursor = lastRet;
            MyArrayList.this.remove(lastRet);
        }

        @Override
        public void set(T t) {
            MyArrayList.this.set(cursor, t);
        }

        @Override
        public void add(T t) {
            MyArrayList.this.add(cursor++, t);
        }
    }

    @Override
    public String toString() {
        Iterator<T> it = iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (; ; ) {
            T e = it.next();
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
