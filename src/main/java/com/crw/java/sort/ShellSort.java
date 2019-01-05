package com.crw.java.sort;

/**
 * 希尔排序，又称“缩小增量排序”，也是插入排序的一种。
 * <p>
 * 希尔排序的具体实现思路是：先将整个记录表分割成若干部分，分别进行直接插入排序，然后再对整个记录表进行一次直接插入排序。
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {4, 2, 56, 23, 12, 33, 18, 24};
        shellSort(arr);
        System.out.print("最终结果：\t");
        for (int i : arr) {
            System.out.print(i + "\t");
        }

        int[] arr2 = {4, 2, 56, 23, 12, 33, 18, 24};
        sort(arr2);
        System.out.print("最终结果：\t");
        for (int i : arr2) {
            System.out.print(i + "\t");
        }
    }


    /**
     * 一次希尔插入排序
     *
     * @param arr 待排序数组
     * @param dk  增量dlta
     */
    private static void shellInsert(int[] arr, int dk) {
        for (int i = dk; i < arr.length; i++) {
            if (arr[i] < arr[i - dk]) { // 需要将arr[i]插入有序增量子表
                int temp = arr[i]; // 暂存arr[i]
                int j;
                for (j = i - dk; j >= 0 && temp < arr[j]; j -= dk) {
                    arr[j + dk] = arr[j]; // 记录后移
                }
                arr[j + dk] = temp; // 插入
            }
        }
    }

    /**
     * 通过调用不同的增量值（记录），实现对多个子表分别进行直接插入排序
     */
    private static void shellSort(int[] arr) {
        int[] dlta = dlta(arr.length);
        for (int i = 0; i < dlta.length; i++) {
            shellInsert(arr, dlta[i]);
        }
    }

    /**
     * 获取步长dk的数组
     *
     * @param len 元素组长度
     * @return
     */
    private static int[] dlta(int len) {
        int[] dlta = new int[(int) Math.sqrt(len) + 1];
        int gap = len / 2;
        int i = 0;
        while (gap > 0) {
            dlta[i] = gap;
            gap /= 2;
            i++;
        }
        return dlta;
    }


    /**
     * 希尔排序2
     *
     * @param arr
     */
    private static void sort(int[] arr) {
        int j; // 插入的位置
        for (int gap = arr.length / 2; gap > 0; gap /= 2) { // 步长
            for (int i = gap; i < arr.length; i++) {
                if (arr[i] < arr[i - gap]) { // 则进行一次希尔插入
                    int tmp = arr[i];
                    for (j = i; j >= gap && tmp < arr[j - gap]; j -= gap) {
                        arr[j] = arr[j - gap]; // 后移gap
                    }
                    arr[j] = tmp; // 插入
                }
            }
        }
    }
}
