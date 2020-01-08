package com.ecacho.nearestplace.generator; /******************************************************************************
 *  Compilation:  javac KdTreeGenerator.java
 *  Execution:    java KdTreeGenerator n
 *  Dependencies: 
 *
 *  Creates n random points in the unit square and print to standard output.
 *
 *  % java KdTreeGenerator 5
 *  0.195080 0.938777
 *  0.351415 0.017802
 *  0.556719 0.841373
 *  0.183384 0.636701
 *  0.649952 0.237188
 *
 ******************************************************************************/


import java.util.Random;

public class KdTreeGenerator {


    private static Random RANDOM = new Random(System.currentTimeMillis());
    private static String CHARS_FOR_NAME =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int i = 0; i < n; i++) {
            double x = uniform(0.0, 1.0);
            double y = uniform(0.0, 1.0);

            String name = randomName(10);
            String line = String.format("%s %8.6f %8.6f", name, x, y);
            System.out.println(line);
        }
    }

    private static String randomName(int size) {
        StringBuilder sb = new StringBuilder();
        int len = CHARS_FOR_NAME.length();

        for (int i = 0; i < size; i++) {
            int idx = uniform(0, len);
            sb.append(CHARS_FOR_NAME.charAt(idx));
        }

        return sb.toString();
    }

    private static int uniform(int a, int b) {
        if (b > a && (long)b - (long)a < 2147483647L) {
            return a + uniform(b - a);
        } else {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
    }

    public static int uniform(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("argument must be positive: " + n);
        } else {
            return RANDOM.nextInt(n);
        }
    }

    public static double uniform(double a, double b) {
        if (a >= b) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        } else {
            return a + uniform() * (b - a);
        }
    }

    public static double uniform() {
        return RANDOM.nextDouble();
    }

}
