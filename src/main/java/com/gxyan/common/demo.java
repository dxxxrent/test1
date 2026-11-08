package com.gxyan.common;

public class demo {
    public static void main(String[] args) {
//        int n =3;
//        System.out.println(getSum(n));
        try {
            test(new int[]{0, 1, 2, 3, 4, 5}); // 调用 test 方法，传入一个数组
        } catch (Exception e) {
            System.out.print("E"); // 如果 main 方法中发生异常，打印 "E"
        }

    }

    public static int getSum(int n){
        return (n==1)?1:n+getSum(n-1);
    }

    private static void test(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            try {
                if (arr[i] % 2 != 0) {
                    throw new NullPointerException(); // 抛出一个 NullPointerException 异常
                } else {
                    System.out.print(i + " "); // 如果是偶数，打印索引
                }
            } catch (NullPointerException e) {
                // 捕获异常，可以在这里处理异常（这里不做任何处理）
            } finally {
                System.out.print("e "); // 无论如何都会打印 "e"
            }
        }
    }
}
