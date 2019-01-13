package com.crw.java.sort;

/**
 * 基数排序
 * <p>
 * 基数排序不同于之前所介绍的各类排序，前边介绍到的排序方法或多或少的是通过使用比较和移动记录来实现排序，
 * 而基数排序的实现不需要进行对关键字的比较，只需要对关键字进行“分配”与“收集”两种操作即可完成。
 * <p>
 * <p>
 * 时间复杂度(d 为位数，r 为基数，n 为数组个数)：
 * 平均情况:O(d*(n+r))
 * 最坏情况:O(d*(n+r))
 * 最好情况:O(d*(n+r))
 * <p>
 * 动图：https://www.cs.usfca.edu/~galles/visualization/RadixSort.html
 */
public class RadixSort {

    public static void main(String[] args) {
        int[] arr = {4, 2, 5601, 2303, 72897, 3312, 189, 241548};
        sort(arr);
        for (int i : arr) {
            System.out.print(i + "\t");
        }
    }

    public static void sort(int[] arr) {
        int max = getMax(arr);
        int len = arr.length;

        // 1.按位次遍历: 需要遍历的次数由数组最大值的位数来决定
        for (int i = 1; max / i > 0; i *= 10) {
            int[][] buckets = new int[10][len];

            // 2.分配操作：获取每一位数字(个、十、百、千位...分配到桶子里)
            for (int j = 0; j < len; j++) {

                int num = (arr[j] / i) % 10;

                //将其放入桶子里
                buckets[num][j] = arr[j];
            }

            // 3.收集操作：回收桶子里的元素
            int k = 0;
            // 遍历这10个桶
            for (int j = 0; j < 10; j++) {
                // 对每个桶子里的元素进行回收
                for (int p = 0; p < len; p++) {
                    //如果桶子里面有元素就回收(数据初始化会为0)
                    if (buckets[j][p] != 0) {
                        arr[k++] = buckets[j][p];
                    }
                }
            }

            print(arr, i);
        }


    }

    private static void print(int[] arr, int d) {
        System.out.print("第:" + d + "位次:\t\t");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + " ");
            if (j == arr.length - 1) {
                System.out.println();
            }
        }
    }

    private static int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[i - 1]) {
                max = arr[i];
            }
        }
        return max;
    }
}
