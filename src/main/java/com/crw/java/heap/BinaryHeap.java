package com.crw.java.heap;

import com.sun.tools.javac.util.List;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * 二叉堆
 * 必须满足结构性和堆序性
 * <p>
 * 1.结构性：满足完全二叉树。
 * 这里内部用数组存储数据，满足条件：
 * 数组中任意位置 i，其左儿子在位置 2i 上，其右儿子在其左儿子后一单元即(2i+1)，其父在位置(i/2)上。
 * 除此以外，问题在于提前预估堆的大小。留有一个位置0.
 * 2.堆序性：保证每个节点X，X的父节点小于等于X，根节点除外
 *
 * @param <T>
 */
public class BinaryHeap<T extends Comparable<? super T>> {

    private int currentSize; // 当前堆大小
    private T[] array; // 堆数据

    // 构造器 start
    public BinaryHeap() {
    }

    public BinaryHeap(int capacity) {
        currentSize = 0;
        array = (T[]) new Comparable[(capacity * 2) + 1];
    }

    public BinaryHeap(T[] items) {
        currentSize = items.length;
        array = (T[]) new Comparable[(currentSize * 2) + 1];
        int i = 1;
        for (T item : items) {
            array[i++] = item;
        }
        buildHeap();
    }

    /**
     * 从倒数第二层开始下滤
     */
    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--) {
            precolateDown(i);
        }
    }

    // 构造器 end

    /**
     * 插入一个新元素
     * 从尾上滤，满足堆序性
     *
     * @param item
     */
    public void insert(T item) {
        if (currentSize == array.length - 1) {
            enlargeArray(array.length * 2 + 1);
        }
        // 上滤
        int hole = ++currentSize; // 保证子树只有左孩子的话也能正确找到父节点
        for (; hole > 1 && item.compareTo(array[hole / 2]) < 0; hole /= 2) {
            array[hole] = array[hole / 2];
        }
        array[hole] = item;
    }

    /**
     * 扩容数组
     *
     * @param newSize
     */
    private void enlargeArray(int newSize) {
        array = Arrays.copyOf(array, newSize);
    }

    /**
     * 最小元素，在1的位置上
     *
     * @return
     */
    public T findMin() {
        return array[1];
    }

    /**
     * 删除最小元素，要从位置1开始下滤
     * <p>
     * 此时将堆中最后一个元素移到堆顶，然后自上而下调整，将该结点与左右孩子结点比较，此时会有三种情况：
     * （1）结点的左右孩子均为空，此时调整结束；
     * （2）结点只有左孩子，此时将该结点与其左孩子比较。若结点大于其左孩子，则两者交换，否则调整结束；
     * （3）结点左右孩子都非空，则将该结点与左右孩子之间的较小者比较，若小于则交换，否则调整结束；
     * <p>
     * 重复此过程，直到该结点不大于其左右孩子结点，或者该结点为叶子结点。
     *
     * @return
     */
    public T deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T item = findMin(); // 找到最小元素
        array[1] = array[currentSize--]; // 删除最小元素，最后元素推到堆顶
        precolateDown(1); // 开始下滤，找到这个最后元素适合的位置
        return item;

    }

    /**
     * 下滤，用于删除操作
     *
     * @param hole
     */
    private void precolateDown(int hole) {
        int child = 0;
        T tmp = array[hole]; // 暂存空洞值
        while (hole * 2 <= currentSize) { // 遍历子树
            child = hole * 2; // 左孩子
            if (child != currentSize && array[child + 1].compareTo(array[child]) < 0) { // 如果右孩子小，选择右孩子
                child++;
            }
            if (array[child].compareTo(tmp) < 0) { // 孩子节点小，交换下滤
                array[hole] = array[child];
            } else {
                break;
            }

            hole = child;
        }
        array[hole] = tmp; // 空洞位置找到，赋值
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[]{22, 31, 5, 13, 17, 8, 10, 7};
        BinaryHeap<Integer> heap = new BinaryHeap<>(array);
        System.out.println(heap.findMin());
        heap.insert(2);
        System.out.println(heap.findMin());
        heap.deleteMin();
        heap.deleteMin();
        System.out.println(heap.findMin());
    }
}
