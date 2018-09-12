package com.crw.java.hashtable;

import java.util.LinkedList;
import java.util.List;

/**
 * 哈希表：使用分离链接法解决哈希冲突
 * <p>
 * 分离链接法：将散列到同一个值得全部元素保留到一个表中，每次查找先 hash()找到要遍历的表，然后遍历该表找到元素
 * 装填因子(Load Factor) λ ：散列表中的元素个数对该表大小的比。
 * 散列表大小不重要，装填因子才重要。分离链接法的装填因子约等于1左右最好。
 * 一次成功的查找时间约为 1+λ/2 。
 *
 * @param <T>
 */
public class SeparateChainingHashTable<T> {

    private static final int DEFAULT_TABLE_SIZE = 11;
    private int currentSize;
    private List<T>[] theLists;

    public SeparateChainingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    public SeparateChainingHashTable(int size) {
        //对数组中的List进行初始化
        theLists = new LinkedList[nextPrime(size)];
        for (int i = 0; i < theLists.length; i++) {
            theLists[i] = new LinkedList<T>();
        }
    }

    /**
     * 清空表
     */
    public void makeEmpty() {
        for (List<T> list : theLists) {
            list.clear();
        }
        currentSize = 0;
    }

    /**
     * 插入元素
     * hash找到链表，如果以存在就不插入，
     * 插入时需要判断长度，如果元素个数达到theLists大小时，为保证装填因子为1，需要扩容 rehash。
     *
     * @param t
     */
    public void insert(T t) {
        List<T> whichList = theLists[myHash(t)];
        if (!contains(t)) {
            whichList.add(t);
            currentSize++;
            if (currentSize > theLists.length) {
                reHash();
            }
        }
    }

    /**
     * 删除元素
     * hash找到链表，如果包含就从链表中删除
     *
     * @param t
     */
    public void remove(T t) {
        List<T> whichList = theLists[myHash(t)];
        if (contains(t)) {
            whichList.remove(t);
            currentSize--;
        }
    }

    /**
     * 扩容再散列
     * 新建一个两倍大的表，然后把旧集合的元素重新插入到新的表里，需要重新计算元素在表中的位置。
     */
    private void reHash() {
        List<T>[] oldLists = theLists;//复制一下一会要用    theLists在又一次new一个
        theLists = new LinkedList[nextPrime(2 * theLists.length)];
        for (int i = 0; i < theLists.length; i++) {
            theLists[i] = new LinkedList<T>();
        }
        //重新插入元素，调用 insert 方法，重新计算其在表中的位置。
        for (int i = 0; i < oldLists.length; i++) {
            for (T t : oldLists[i]) {
                insert(t);
            }
        }

    }

    /**
     * 是否包含
     * hash找到链表，然后遍历链表查询是否存在
     *
     * @param t
     * @return
     */
    public boolean contains(T t) {
        //通过myHash找出是哪一个集合
        List<T> whichList = theLists[myHash(t)];
        return whichList.contains(t);
    }

    /**
     * hash算法，此处hashCode对表大小取模处理
     *
     * @param t
     * @return
     */
    private int myHash(T t) {
        int hash = t.hashCode();
        hash %= theLists.length;
        //防止hash值为负数
        if (hash < 0) {
            hash += theLists.length;
        }
        return hash;
    }

    /**
     * 表的大小是一个素数,这能够保证一个非常好的分布
     * 是否是素数
     *
     * @param num
     * @return
     */
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
        SeparateChainingHashTable<Employee> sc = new SeparateChainingHashTable<>();
        System.out.println(sc.contains(e));
        sc.insert(e);
        System.out.println(sc.contains(e));
        sc.remove(e);
        System.out.println(sc.contains(e));
    }
}

/*
 * 想把类放在散列表中 必须提供两个方法。
 * 1. equals方法，由于要在list的集合中进行查找contains方法时会用到equals进行比較
 * 2. hashCode方法，由于须要通过它来找出Employee对象该放在哪一个集合中（找出数组的相应序号）
 */
class Employee {
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Employee && name.equals(((Employee) obj).name);
    }

    @Override
    public int hashCode() {
        //String类是有自己的hashCode方法
        return name.hashCode();
    }
    /*// String类中hashCode方法
    public int hashCode() {
        int h = hash;
        if (h == 0 && value.length > 0) {
            char val[] = value;

            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
            hash = h;
        }
        return h;
    }*/
}
