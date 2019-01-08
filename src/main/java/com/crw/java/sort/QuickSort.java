package com.crw.java.sort;

/**
 * 快速排序：对冒泡排序的一种改进
 * <p>
 * 基本思想是：通过一次排序将整个无序表分成相互独立的两部分，其中一部分中的数据都比另一部分中包含的数据的值小，
 * 然后继续沿用此方法分别对两部分进行同样的操作，直到每一个小部分不可再分，所得到的整个序列就成为了有序序列。
 * <p>
 * 分治思想。
 * <p>
 * 基本步骤：
 * 1.先从数列中取出一个数作为基准数。
 * 2.分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
 * 3.再对左右区间重复第二步，直到各区间只有一个数
 * <p>
 * 时间复杂度：O(N*logN)
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {4, 2, 56, 23, 12, 33, 18, 24};
        sort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    public static void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static int partition(int[] arr, int low, int high) {
        int pivotkey = arr[low]; // 子表第一个记录做枢轴记录key
        while (low < high) {
            while (arr[high] >= pivotkey && high > low) { // 从后往前扫，将小的数移到低端
                high--;
            }
            arr[low] = arr[high];
            while (arr[low] <= pivotkey && high > low) { // 从前往后扫，将大的数移到高端
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = pivotkey;

        print(arr, low, high, pivotkey);
        return low;
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        int pivotloc = partition(arr, low, high); // 将arr一分为二，pivotloc是枢轴位置
        quickSort(arr, low, pivotloc - 1); // 对低子表递归
        quickSort(arr, pivotloc + 1, high); // 对高子表递归
    }

    private static void print(int[] arr, int low, int high, int pivotkey) {
        System.out.print("low:" + low + "\t,high:" + high + "\t,基准数:" + pivotkey + "\t,此轮快排结果:\t");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + " ");
            if (j == arr.length - 1) {
                System.out.println();
            }
        }
    }
}
