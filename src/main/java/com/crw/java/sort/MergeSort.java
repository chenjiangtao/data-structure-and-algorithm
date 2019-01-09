package com.crw.java.sort;

/**
 * 归并排序
 * <p>
 * 基本思想：
 * 归并排序（MERGE-SORT）是利用归并的思想实现的排序方法，
 * 该算法采用经典的分治（divide-and-conquer）策略（分治法将问题分(divide)成一些小的问题然后递归求解，
 * 而治(conquer)的阶段则将分的阶段得到的各答案"修补"在一起，即分而治之)。
 * <p>
 * 时间复杂度：O(nlogn)。对比于堆排序和快速排序，它是稳定的排序。
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {4, 2, 56, 23, 12, 33, 18, 24};
        sort(arr);
        for (int i : arr) {
            System.out.print(i + "\t");
        }
    }

    public static void sort(int[] arr) {
        int[] temp = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, temp);
    }

    /**
     * 合并两个有序子表，将 arr[left...mid] 和 arr[mid+1...right] 合并为 temp[left...right]
     *
     * @param arr
     * @param left
     * @param mid
     * @param right
     * @param temp
     */
    private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left;//左序列指针
        int j = mid + 1;//右序列指针
        int k = 0;//临时数组指针

        // i:左序列指针, j:右序列指针, k:临时数组指针；循环将arr中记录并入temp中
        for (; i <= mid && j <= right; k++) {
            if (arr[i] < arr[j]) {
                temp[k] = arr[i++];
            } else {
                temp[k] = arr[j++];
            }
        }
        while (i <= mid) {//将左边剩余元素填充进temp中
            temp[k++] = arr[i++];
        }
        while (j <= right) {//将右序列剩余元素填充进temp中
            temp[k++] = arr[j++];
        }


        //将temp中的元素全部拷贝到原数组中
        k = 0;
        while (left <= right) {
            arr[left++] = temp[k++];
        }

    }

    private static void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left == right) { // 递归出口
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(arr, left, mid, temp); // 左边归并排序，使得左子序列有序
        mergeSort(arr, mid + 1, right, temp); // 右边归并排序，使得右子序列有序
        merge(arr, left, mid, right, temp); // 将两个有序子数组合并操作
    }
}
