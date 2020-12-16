package com.simen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Long.max;
import static java.lang.Long.min;

public class Day9 {

    private static List<Long> input = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
            stream.forEach(n -> input.add(Long.parseLong(n)));
        }

        /*
        // PART 1
        for (int i = 25; i < input.size(); i++) {
            if (!findPair(i)) {
                System.out.println(input.get(i));
                break;
            }
        }
        */

        for (int i = 0; i < input.size(); i++) {
            long target = 1124361034;
            if (findSequence(i, target)) break;
        }

    }

    // PART 1
    private static boolean findPair(int idx) {
        for (int i = idx-25; i < idx-1; i++) {
            for (int j = i+1; j < idx; j++) {
                if (input.get(i) + input.get(j) == input.get(idx)) return true;
            }
        }
        return false;
    }

    // PART 2
    private static boolean findSequence(int startIdx, long target) {
        long total = 0, smallest = Long.MAX_VALUE, largest = 0;
        for (int i = startIdx; i < input.size(); i++) {
            total += input.get(i);
            smallest = min(smallest, input.get(i));
            largest = max(largest, input.get(i));
            if (total == target) {
                System.out.println(smallest + largest);
                return true;
            }
        }
        return false;
    }
}
