package com.simen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


public class Day14 {

    private static String mask;
    private static long[] memory = new long[100000]; // PART 1
    private static Map<Long, Long> memoryMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
            stream.forEach(Day14::part2);
        }

        // System.out.println(LongStream.of(memory).sum()); // PART 1
        System.out.println(memoryMap.values().stream().reduce(0L, Long::sum));
    }

    public static void part2(String s) {
        if (s.startsWith("mask")) mask = s.substring(7);
        else {
            int address = Integer.parseInt(s.substring(s.indexOf('[') + 1, s.indexOf(']')));
            final int value = Integer.parseInt(s.substring(s.indexOf('=') + 2));

            String bitRepresentation = Integer.toBinaryString(address);
            StringBuilder result = new StringBuilder(mask);

            for (int i = bitRepresentation.length()-1, j = mask.length()-1; i >= 0 && j >= 0; i--, j--) {
                if (mask.charAt(j) == '0') result.setCharAt(j, bitRepresentation.charAt(i));
            }

            recursivelyAddToMemory(result, value);
        }

    }

    private static void recursivelyAddToMemory(StringBuilder sb, long value) {
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == 'X') {
                sb.setCharAt(i, '0');
                recursivelyAddToMemory(sb, value);
                sb.setCharAt(i, '1');
                recursivelyAddToMemory(sb, value);
                sb.setCharAt(i, 'X');
                return;
            }
        }
        long address = Long.parseLong(sb.toString(), 2);
        memoryMap.put(address, value);
    }

    public static void part1(String s) {
        if (s.startsWith("mask")) mask = s.substring(7);
        else {
            final int address = Integer.parseInt(s.substring(s.indexOf('[')+1, s.indexOf(']')));
            int value = Integer.parseInt(s.substring(s.indexOf('=')+2));

            String bitRepresentation = Integer.toBinaryString(value);
            StringBuilder result = new StringBuilder("0".repeat(mask.length()));

            for (int i = 0; i < mask.length(); i++) {
                if (mask.charAt(i) != 'X') {
                    result.setCharAt(i, mask.charAt(i));
                }
            }

            for (int i = bitRepresentation.length()-1, j = mask.length()-1; i >= 0 && j >= 0; i--, j--) {
                if (mask.charAt(j) == 'X') result.setCharAt(j, bitRepresentation.charAt(i));
            }

            long storedValue = Long.parseLong(result.toString(), 2);
            memory[address] = storedValue;
        }
    }
}
