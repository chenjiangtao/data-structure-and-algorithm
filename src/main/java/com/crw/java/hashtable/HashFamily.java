package com.crw.java.hashtable;

/**
 * 布谷鸟散列通用HashFamily接口
 *
 * @param <T>
 */
public interface HashFamily<T> {

    /**
     * 根据which来选择散列函数，并返回hash值
     *
     * @param x
     * @param which
     * @return
     */
    int hash(T x, int which);

    /**
     * 获取集合中散列函数的个数
     *
     * @return
     */
    int getNumberOfFunctions();

    /**
     * 获取到新的散列函数
     */
    void generateNewFunctions();
}
