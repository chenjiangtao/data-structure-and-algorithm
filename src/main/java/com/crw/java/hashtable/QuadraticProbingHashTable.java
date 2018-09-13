package com.crw.java.hashtable;

/**
 * 哈希表：使用开放定址发中的平方探测法解决hash冲突
 * 装填因子 < 0.5
 * 满足两个条件，使得总能插入一个新的元素：
 * 1.表的大小为素数
 * 2.表至少一半为空
 *
 * @param <T>
 */
public class QuadraticProbingHashTable<T> {

    private static class HashEntry<T> {
        public T elements;
        public boolean isActive; // 为惰性删除提供的变量，false表示已删除

        public HashEntry(T e, boolean i) {
            this.elements = e;
            this.isActive = i;
        }
    }

    private static final int DEFAULT_TABLE_SIZE = 11;
    private HashEntry<T>[] array;
    private int currentSize;


    /**
     * 构造器
     */
    public QuadraticProbingHashTable() {
        this(QuadraticProbingHashTable.DEFAULT_TABLE_SIZE);
    }

    /**
     * 构造器
     *
     * @param size 表初始化大小
     */
    public QuadraticProbingHashTable(int size) {
        this.allocateArray(size);
        this.makeEmpty();
    }


    public void makeEmpty() {
        currentSize = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }

    /**
     * 是否存在
     *
     * @param x
     * @return
     */
    public boolean contains(T x) {
        int currentPos = this.findPos(x);
        return this.isActive(currentPos);
    }

    /**
     * 分配hash桶素组
     *
     * @param arraySize
     */
    private void allocateArray(int arraySize) {
        this.array = new HashEntry[arraySize];
    }

    /**
     * 插入元素
     *
     * @param x
     */
    public void insert(T x) {
        int currentPos = this.findPos(x);
        if (isActive(currentPos)) {
            return;
        }
        array[currentPos] = new HashEntry<T>(x, true);
        if (++currentSize > array.length / 2) {
            this.rehash();
        }
    }

    /**
     * 删除元素，惰性删除
     *
     * @param x
     */
    public void remove(T x) {
        int currentPos = this.findPos(x);
        if (this.isActive(currentPos)) {
            array[currentPos].isActive = false;
            this.currentSize--;
        }
    }

    /**
     * 再散列
     */
    private void rehash() {
        HashEntry<T>[] oldArray = this.array;
        this.allocateArray(nextPrime(this.array.length * 2));
        this.currentSize = 0;
        for (int i = 0; i < oldArray.length; i++) {
            if (oldArray[i] != null && oldArray[i].isActive) {
                this.insert(oldArray[i].elements);
            }
        }
    }

    /**
     * 判断当前位置是否有元素且元素未删除状态
     *
     * @param currentPos
     * @return
     */
    private boolean isActive(int currentPos) {
        return array[currentPos] != null && array[currentPos].isActive;
    }

    /**
     * 定址
     *
     * @param x
     * @return
     */
    private int findPos(T x) {
        int offset = 1; // 默认偏移量
        int currentPos = myHash(x); // hash算的当前位置
        while (array[currentPos] != null && !array[currentPos].elements.equals(x)) { // hash冲突的情况
            currentPos += offset;
            offset += 2;
            if (currentPos >= this.array.length) {
                currentPos -= array.length;
            }
        }
        return currentPos;
    }

    private int myHash(T x) {
        int hashVal = x.hashCode() % this.array.length;
        return hashVal > 0 ? hashVal : (hashVal + this.array.length);
    }

    private static boolean isPrime(int num) {
        int i = 1;
        while ((num % (i + 1)) != 0) {
            i++;
        }
        if (i == num - 1) {
            return true;
        } else {
            return false;
        }
    }

    private static int nextPrime(int num) {
        while (!isPrime(num)) {
            num++;
        }
        return num;
    }

    public static void main(String[] args) {
        Employee e = new Employee("chenruiwen");
        QuadraticProbingHashTable<Employee> sc = new QuadraticProbingHashTable<>();
        System.out.println(sc.contains(e));
        sc.insert(e);
        System.out.println(sc.contains(e));
        sc.remove(e);
        System.out.println(sc.contains(e));
    }
}
