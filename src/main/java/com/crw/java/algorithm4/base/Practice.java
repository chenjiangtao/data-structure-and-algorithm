package com.crw.java.algorithm4.base;

import algs4.cs.princeton.edu.StdOut;

public class Practice {

    public static void main(String[] args) {
        practice1();
        practice2();
        practice6();
        practice7();
        practice8();
        practice9();
        practice12();
        practice13();
        practice15();
    }


    static void practice1() {
        StdOut.println("practice1 start...");
        StdOut.println((0 + 15) / 2); // 7 类型是整形，所以输出7
        StdOut.println(2.0e-6 * 100000000.1); // 200.0000002 因为浮点型
        StdOut.println(true && false || true && true); // true 因为false||true，输出
        StdOut.println("practice1 end...");
    }

    static void practice2() {
        StdOut.println("practice2 start...");
        StdOut.println((1 + 2.236) / 2); // 1.618
        StdOut.println(1 + 2 + 3 + 4.0); // 10.0
        StdOut.println(4.1 >= 4); // true
        StdOut.println(1 + 2 + "3"); // 33 数字转化为字符串
        StdOut.println("practice2 end...");
    }

    // 这是一段斐波那契额数列
    static void practice6() {
        StdOut.println("practice6 start...");
        int f = 0;
        int g = 1;
        for (int i = 0; i <= 15; i++) {
            StdOut.println(f);
            f = f + g;
            g = f - g;
        }
        StdOut.println("practice6 end...");
    }

    // 输出结果为什么
    static void practice7() {
        StdOut.println("practice7 start...");
        //a.
        double t = 9.0;
        while (Math.abs(t - 9.0 / t) > .001)
            t = (9.0 / t + t) / 2.0;
        StdOut.printf("%.5f\n", t);
        //b.
        int sum = 0;
        for (int i = 1; i < 1000; i++)
            for (int j = 0; j < i; j++)
                sum++;
        StdOut.println(sum);
        //c.
        int sum2 = 0;
        for (int i = 1; i < 1000; i *= 2)
            for (int j = 0; j < 1000; j++)
                sum2++;
        StdOut.println(sum2);
        StdOut.println("practice7 end...");
    }

    static void practice8() {
        StdOut.println("practice8 start...");
        StdOut.println('b'); // b
        StdOut.println('b' + 'c'); // 197 ,b的ASCII码为98，c的为99
        StdOut.println((char) ('a' + 4)); // e
        StdOut.println("practice8 end...");
    }

    // 编写一段代码，将一个正整数 N 用二进制表示并转换为一个 String 类型的值 s
    static void practice9() {
        StdOut.println("practice9 start...");
        int N = 18;
        StdOut.println(Integer.toBinaryString(N)); // 内置

        String s = "";
        for (int n = N; n > 0; n /= 2) {
            s = (n % 2) + s;
        }
        StdOut.println(s);

        StdOut.println("practice9 end...");
    }

    // 输出结果回文了
    static void practice12() {
        StdOut.println("practice12 start...");
        int[] a = new int[10];
        for (int i = 0; i < 10; i++)
            a[i] = 9 - i;
        for (int i = 0; i < 10; i++)
            a[i] = a[a[i]];
        for (int i = 0; i < 10; i++)
            System.out.println(a[i]);
        StdOut.println("practice12 end...");
    }

    // 编写一段代码，打印出一个 M 行 N 列的二维数组的转置(交换行和列)。
    static void practice13() {
        StdOut.println("practice13 start...");
        int[][] a = {{1, 2, 3}, {4, 5, 6}};

        int[][] temp = new int[a[0].length][a.length];
        for (int i = 0; i < a[0].length; i++) {
            for (int j = 0; j < a.length; j++) {
                temp[i][j] = a[j][i];
                System.out.print(temp[i][j] + " ");
                if (j == a.length - 1)
                    System.out.println();
            }
        }
        StdOut.println("practice13 end...");
    }

    /**
     * 编写一个静态方法 histogram()，接受一个整型数组 a[] 和一个整数 M 为参数并返回一个大小为 M 的数组，
     * 其中第 i 个元素的值为整数 i 在参数数组中出现的次数。如果 a[] 中的值均在 0 到 M-1之间，
     * 返回数组中所有元素之和应该和 a.length 相等。
     * <p>
     * 看题目略懵，实际是展示一个直方图
     */
    static void practice15() {
        StdOut.println("practice15 start...");
        int a[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 11, 11, 12};
        int M = 13;
        int b[] = histogram(a, M);
        StdOut.println("result：");
        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }
        StdOut.println("practice15 end...");
    }

    public static int[] histogram(int[] a, int M) {
        int[] h = new int[M];
        int N = a.length;
        for (int i = 0; i < N; i++)
            if (a[i] < M)
                h[a[i]]++;
        return h;
    }
}
