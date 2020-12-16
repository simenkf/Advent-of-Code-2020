package com.simen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Day10 {

    private static List<Integer> input = new ArrayList<>(Arrays.asList(0));
    private static List<Long> dp; // PART 2

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
            stream.mapToInt(Integer::parseInt).sorted().forEach(input::add);
        }

        // PART 1
        /*
        int oneCount = 0, threeCount = 1;

        for (int i = 1; i < input.size(); i++) {
            int diff = input.get(i) - input.get(i-1);
            if (diff == 1) oneCount++;
            if (diff == 3) threeCount++;
        }

        System.out.println(oneCount * threeCount);
        */

        // PART 2
        dp = new ArrayList<Long>(Collections.nCopies(input.size(), 0L));
        dp.set(0, 1L);

        for (int i = 0; i < input.size(); i++) {
            int currJolt = input.get(i);

            for (int j = 1; i+j < input.size() && input.get(i+j) - currJolt <= 3; j++) {
                long newCount = dp.get(i) + dp.get(i+j);
                dp.set(i+j, newCount);
            }
        }
        System.out.println(dp.get(dp.size()-1));
    }

}