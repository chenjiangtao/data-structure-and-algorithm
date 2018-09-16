package com.crw.java.hashtable;

import java.util.Random;

/**
 * 布谷鸟散列表
 * 1. 提供2个及以上的hash函数
 * 2. 装填因子应当小于0.5
 * 3. 理论上维护2张半空的表，此处只有一张表
 * <p>
 * hash冲突的解决方式(详见insertHelper方法):
 * 发送hash冲突的时候，轮询或随机调用这里的多个hash函数找到新位置，新旧位置的值替换直至找到空位置。
 * 根据rehash的次数判断rehash还是扩展表
 *
 * @param <T>
 */
public class CuckooHashTable<T> {
    // 最大装填因子为0.4
    private static final double MAX_LOAD = 0.4;
    // 允许的rehash次数
    private static final int ALLOWED_REHASHES = 1;
    // 默认表的大小
    private static final int DEFAULT_TABLE_SIZE = 101;


    // 散列函数集合
    private final HashFamily<? super T> hashFunctions;
    // 散列函数个数
    private final int numHashFunctions;
    // 数据
    private T[] array;
    // 当前表的大小
    private int currentSize;


    // 当前表已经rehash的次数
    private int rehashes = 0;
    // 随机数
    private Random r = new Random();

    // 构造hash表  start
    public CuckooHashTable(HashFamily<? super T> hf) {
        this(hf, DEFAULT_TABLE_SIZE);
    }


    public CuckooHashTable(HashFamily<? super T> hf, int size) {
        allocateArray(nextPrime(size));
        doClear();
        hashFunctions = hf;
        numHashFunctions = hf.getNumberOfFunctions();
    }

    // 构造hash表  end


    // 插入方法是最难写 start

    /**
     * 插入：
     * 1. 判断是否已存在
     * 2. 判断是否到满载，是就扩展
     * 3. 插入
     *
     * @param x
     * @return
     */
    public boolean insert(T x) {
        if (contains(x)) {
            return false;
        }
        if (currentSize >= array.length * MAX_LOAD) {
            expand();
        }
        return insertHelper(x);
    }

    private boolean insertHelper(T x) {
        // 循环的最大次数
        final int COUNT_LIMIT = 100;
        while (true) {
            // 上一个元素位置
            int lastPos = -1;
            int pos;
            // 进行查找插入
            for (int count = 0; count < COUNT_LIMIT; count++) {
                // 1.使用其中一个hash函数能找到值直接返回
                for (int i = 0; i < numHashFunctions; i++) {
                    pos = myHash(x, i);
                    //查找成功，直接返回
                    if (array[pos] == null) {
                        array[pos] = x;
                        currentSize++;
                        return true;
                    }
                }
                // 2.hash冲突，进行替换操作。使用随机hash函数获取占位符查找位置
                int i = 0;
                do {
                    pos = myHash(x, r.nextInt(numHashFunctions));
                } while (pos == lastPos && i++ < 5); // 避免两个hash函数同时定位到同一位置，限制hash次数
                // 3.进行替换操作，新旧位置替换
                T temp = array[lastPos = pos];
                array[pos] = x;
                x = temp;
            }
            // 4. 进行rehash或者扩展表，如果rehash次数达到临界值，则扩展，否则rehash
            if (++rehashes > ALLOWED_REHASHES) {
                expand();
                rehashes = 0;
            } else {
                rehash();
            }
        }
    }

    /**
     * 扩容
     */
    private void expand() {
        rehash((int) (array.length / MAX_LOAD));
    }

    private void rehash() {
        hashFunctions.generateNewFunctions();
        rehash(array.length);
    }

    /**
     * rehash,与其他hash表rehash操作大致相同，但此处没有扩容，至少重新插入rehash
     *
     * @param newLength
     */
    private void rehash(int newLength) {
        T[] oldArray = array;
        allocateArray(nextPrime(newLength));
        currentSize = 0;
        for (T str : oldArray) {
            if (str != null) {
                insert(str);
            }
        }
    }
    // 插入方法是最难写 end


    public boolean remove(T x) {
        int pos = findPos(x);

        if (pos != -1) {
            array[pos] = null;
            currentSize--;
        }
        return pos != -1;
    }

    public boolean contains(T x) {
        return findPos(x) != -1;
    }

    /**
     * 查找元素位置，需遍历所有的散列函数查找
     * 查到返回位置，否则返回 -1
     *
     * @param x
     * @return
     */
    private int findPos(T x) {
        for (int i = 0; i < numHashFunctions; i++) {
            int pos = myHash(x, i);
            if (array[pos] != null && array[pos].equals(x)) {
                return pos;
            }
        }
        return -1;
    }


    /**
     * hash函数，使用 hashFunctions 的实现
     *
     * @param x
     * @param which
     * @return
     */
    private int myHash(T x, int which) {
        int hashVal = hashFunctions.hash(x, which);
        hashVal %= array.length;
        if (hashVal < 0) {
            hashVal += array.length;
        }
        return hashVal;
    }

    public void makeEmpty() {
        doClear();
    }

    // 基础方法 start
    private void doClear() {
        currentSize = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }

    private void allocateArray(int arraySize) {
        array = (T[]) new Object[arraySize];
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
    // 基础方法 end

    public static void main(String[] args) {
        // 自定义hash方法
        HashFamily<String> hashFamily = new HashFamily<String>() {
            //根据which选取不同的散列函数
            @Override
            public int hash(String x, int which) {
                int hashVal = 0;
                switch (which) {
                    case 0: {
                        for (int i = 0; i < x.length(); i++) {
                            hashVal += x.charAt(i);
                        }
                        break;
                    }
                    case 1:
                        for (int i = 0; i < x.length(); i++) {
                            hashVal = 37 * hashVal + x.charAt(i);
                        }
                        break;
                }
                return hashVal;
            }

            @Override
            public int getNumberOfFunctions() {
                return 2;
            }

            @Override
            public void generateNewFunctions() {
            }
        };

        CuckooHashTable<String> cuckooHashTable = new CuckooHashTable<String>(hashFamily, 5);
        String[] strs = {"abc", "aba", "abcc", "abca"};
        //插入
        for (int i = 0; i < strs.length; i++) {
            cuckooHashTable.insert(strs[i]);
        }
        //打印表
        Object[] array = cuckooHashTable.array;
        System.out.println("size：" + cuckooHashTable.currentSize);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null)
                System.out.println("pos: " + i + " value: " + array[i]);
        }
    }
}
