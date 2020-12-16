package com.simen;

import java.util.HashMap;
import java.util.Map;

public class Day15 {

    final private static Map<Integer, Integer> numbersMap = new HashMap<>(
            Map.of(0, 1, 1, 2, 5, 3, 10, 4, 3, 5, 12, 6, 19, 7));

    public static void main(String[] args) {
        int totalNumbers = 7, currentNumber = 19, prevIdxOfNumb = -1;

        while (totalNumbers++ < 30_000_000) { // 2020 for PART 1
            currentNumber = prevIdxOfNumb == -1 ? 0 : totalNumbers - prevIdxOfNumb - 1;
            prevIdxOfNumb = numbersMap.getOrDefault(currentNumber, -1);
            numbersMap.put(currentNumber, totalNumbers);
        }
        System.out.println(currentNumber);
    }
}