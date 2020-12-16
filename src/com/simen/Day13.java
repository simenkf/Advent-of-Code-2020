package com.simen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day13 {

    private static int ts = -1;
    private static List<Integer> buses = new ArrayList<>();
    private static int[] remainders;
    private static int[] numbers;

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
            stream.forEach(Day13::processInputPart2);
        }

        // PART 1
        for (int bus : buses) {
            int waitTime = bus - ts % bus;
            System.out.println(waitTime + " " + waitTime * bus);
        }

        // PART 2
        System.out.println(CRT(remainders, numbers));

    }

    public static void processInput(String s) {
        if (ts == -1) {
            ts = Integer.parseInt(s);
        } else {
            for (String b : s.split(",")) {
                if (b.equals("x")) {
                    continue;
                }
                buses.add(Integer.parseInt(b));
            }
        }
    }

    public static void processInputPart2(String s) {
        if (ts == -1) {
            ts = Integer.parseInt(s);
        } else {
            String[] temp = s.split(",");

            int count = 0;
            for (String b : temp) {
                if (!b.equals("x")) count++;
            }

            numbers = new int[count];
            remainders = new int[count];

            int idx = 0;
            for (int i = 0; i < temp.length; i++) {
                if (temp[i].equals("x")) continue;

                numbers[idx] = Integer.parseInt(temp[i]);
                remainders[idx++] = Integer.parseInt(temp[i]) - i;
            }
        }
    }

    // Chinese Remainder Theorem
    public static long CRT(int[] rem, int[] num) {
        long product = 1;
        for (int i = 0; i < num.length; i++ ) {
            product *= num[i];
        }

        long partialProduct[] = new long[num.length];
        long inverse[] = new long[num.length];
        long sum = 0;

        for (int i = 0; i < num.length; i++) {
            partialProduct[i] = product / num[i]; // floor division
            inverse[i] = computeInverse(partialProduct[i], num[i]);
            sum += partialProduct[i] * inverse[i] * rem[i];
        }
        System.out.println(sum);
        return sum % product;
    }

    public static long computeInverse(long a, long b) {
        long m = b, t, q;
        long x = 0L, y = 1L;

        if (b == 1L) return 0;

        // Apply extended Euclid Algorithm
        while (a > 1L) {
            // q is quotient
            q = a / b;
            t = b;

            // m is remainder now, process same as general Euclid's algorithm
            b = a % b;
            a = t;
            t = x;
            x = y - q * x;
            y = t;
        }

        // Make x1 positive
        if (y < 0L) y += m;

        return y;
    }
}
