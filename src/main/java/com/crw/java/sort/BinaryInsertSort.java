package com.crw.java.sort;

/**
 * 折半插入排序
 * <p>
 * 时间复杂度：O(n^2)；空间复杂度为O(1)；稳定的排序
 * 相对于直接插入只是减少了比较的次数，但元素移动的次数不变
 */
public class BinaryInsertSort {

    public static void main(String[] args) {
        int[] arr = {4, 2, 56, 23, 12, 33, 18, 24};
        sort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    public static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i]; // 暂存待插入元素
            int low = 0;
            int high = i - 1;
            while (low <= high) {
                int mid = (low + high) / 2; // 折半
                if (temp < arr[mid]) { // 插入点在低半区
                    high = mid - 1;
                } else { // 插入点在高半区
                    low = mid + 1;
                }
            }
            for (int j = i - 1; j >= high + 1; j--) { // 记录后移
                arr[j + 1] = arr[j];
            }
            arr[high + 1] = temp; // 插入
            print(arr, i, low);
        }
    }

    private static void print(int[] arr, int i, int low) {
        System.out.print("第" + i + "次结果: ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + " ");
            if (j == arr.length - 1) {
                System.out.print("要插入的索引值：" + low);
                System.out.println();
            }
        }
    }
}
