package com.crw.java.sort;

/**
 * 堆排序，也是一种选择排序
 * <p>
 * 满足堆结构性和堆序性：
 * 1.结构性：完全二叉树
 * 2.堆序性：
 * - ki ≤ k2i+1 且 ki ≤ k2i+2（在 n 个记录的范围内，第 i 个关键字的值小于第 2*i+1 个关键字，同时也小于第 2*i+2 个关键字）-> 小顶堆
 * - ki ≥ k2i+1 且 ki ≥ k2i+2（在 n 个记录的范围内，第 i 个关键字的值大于第 2*i+1 个关键字，同时也大于第 2*i+2 个关键字）-> 大顶堆
 * <p>
 * 需要解决2个问题：
 * 1.如何将得到的无序序列转化为一个堆？
 * 2.在输出堆顶元素之后（完全二叉树的树根结点），如何调整剩余元素构建一个新的堆？
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] arr = {4, 2, 56, 23, 12, 33, 18, 24};
        heapSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    public static void heapSort(int[] arr) {
        // 构建堆的过程
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            // 对于有孩子结点的根结点进行筛选
            heapAdjust(arr, i, arr.length);
        }
        // 调整堆结构+交换堆顶元素与末尾元素
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i); // 交换 堆顶元素 与 最后一个记录
            heapAdjust(arr, 0, i); // 重新调整为堆结构
        }

    }


    /**
     * 堆调整。arr[s...m]范围内的值，除了arr[s]外，其他均满足堆的性质，本函数调整arr[s]的值，使得arr[s...m]成为一个大顶堆。
     *
     * @param arr
     * @param s
     * @param m
     */
    private static void heapAdjust(int[] arr, int s, int m) {
        int rc = arr[s]; // 操作位元素
        for (int j = 2 * s + 1; j < m; j = j * 2 + 1) { // 下滤，从 s 的左孩子(2s+1)开始
            // j为值较大的下标
            if (j + 1 < m && arr[j] < arr[j + 1]) { // 左孩子小与右孩子，指向右孩子
                j++;
            }
            // 找到空洞位置，rc应插入s位置
            if (rc >= arr[j]) {
                break;
            }

            arr[s] = arr[j];
            s = j;
        }
        arr[s] = rc;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
