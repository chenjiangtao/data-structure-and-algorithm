package com.crw.java.sort;

/**
 * 简单选择排序
 * <p>
 * 时间复杂度：O(N^2)
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] arr = {4, 2, 56, 23, 12, 33, 18, 24};
        sort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int j = selectMinKey(arr, i); // 找到剩下子表中最小的key
            if (i != j) { // 不相等说明存在更小的最小值，需要交换
                swap(arr, i, j);
            }

        }
    }

    private static int selectMinKey(int[] arr, int i) {
        int min = i;
        // 从下标为 i+1 开始，一直遍历至最后一个关键字，找到最小值所在的位置v
        while (i + 1 < arr.length - 1) {
            if (arr[min] > arr[i + 1]) {
                min = i + 1;
            }
            i++;
        }
        return min;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
