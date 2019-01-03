package com.crw.java.sort;

/**
 * 2-路插入排序
 * <p>
 * 在折半插入排序的基础上改进，减少排序过程中移动的次数，需要n个记录空间辅助。
 * <p>
 * 核心思想：使用循环数组辅助。两个端点first,last将数组分为两路
 * 参考：https://blog.csdn.net/zhangxiangDavaid/article/details/27958143
 */
public class P2InsertSort {


    public static void main(String[] args) {
        int[] arr = {4, 2, 56, 23, 12, 33, 18, 24};
        sort(arr);
        System.out.print("最终结果：\t");
        for (int i : arr) {
            System.out.print(i + "\t");
        }
    }

    private static void sort(int[] arr) {
        int len = arr.length;
        int first = 0;
        int last = 0;
        int[] tmp = new int[len];
        tmp[0] = arr[0];
        for (int i = 1; i < len; i++) {
            if (arr[i] < tmp[first]) { // 待插入元素比最小的元素小
                System.out.print("插入 1.1 路\t");
                first = (first - 1 + len) % len;
                tmp[first] = arr[i];
            } else if (arr[i] > tmp[last]) { // 待插入元素比最大的元素大
                System.out.print("插入 1.2 路\t");
                // last = (last + 1 + len) % len;
                last++; // 理论上应该按上面这样写，实际上last不会超过 len - 1 。
                tmp[last] = arr[i];
            } else { // 待插入元素比最小大，比最大小
                System.out.print("插入 2 路\t");
                int k;
                // 使用直接插入
                for (k = last + 1; arr[i] < tmp[(k - 1 + len) % len]; k = (k - 1 + len) % len) {
                    tmp[k] = tmp[(k - 1 + len) % len];
                }

                tmp[(k + len) % len] = arr[i];
                last = (last + 1 + len) % len;
            }

            print(tmp, i, first, last);
        }

        // 将排序记录复制到原来的顺序表里
        for (int j = 0; j < len; j++) {
            arr[j] = tmp[(first + j) % len];
        }

    }

    private static void print(int[] arr, int i, int first, int last) {
        System.out.print("第" + i + "次结果:\t");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + "\t");
            if (j == arr.length - 1) {
                System.out.print("first 索引值：" + first + "\t");
                System.out.print("last 索引值：" + last + "\t");
                System.out.println();
            }
        }
    }
}
