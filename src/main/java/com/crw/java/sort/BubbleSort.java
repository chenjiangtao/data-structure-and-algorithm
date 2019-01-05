package com.crw.java.sort;

/**
 * 冒泡排序：交换排序的一种，相邻的两个元素比较，每次冒泡把最大的元素"冒泡交换"到最大的位置
 * <p>
 * 时间复杂度：O(n^2)
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {4, 2, 56, 23, 12, 33, 18, 24};
        sort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) { // 仅需遍历 len - 1 次
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
