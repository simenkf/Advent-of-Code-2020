package com.simen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day7 {
    private static Map<String, List<String>> bagRules = new HashMap<>();
    private static Map<String, Integer> bagCounts = new HashMap<>(); // for PART 2

    public static void main(String[] args) throws IOException {

        try (Stream<String> stream = Files.lines(Paths.get("invalid.txt"))) {
            stream.forEach(Day7::processRules);
        }

        /*
        // PART 1
        int total = 0;
        String myBag = "shiny gold";

        for (String bagColor : bagRules.keySet()) {
            if (bagColor.equals(myBag)) continue;
            if (findMyBag(bagColor, myBag)) total++;
        }

        System.out.println(total);
        */

        // PART 2
        String myBag = "shiny gold";
        int total = computeTotalSubBags(myBag);
        System.out.println(total - 1); // minus 1 since the shiny gold bag it self does not count
    }


    public static void processRules(String rule) {
        final String splitString = " bags contain ";
        final int splitIdx = rule.indexOf(splitString);
        final String bagColor = rule.substring(0, splitIdx);
        String[] allowedSubBags = rule.substring(splitIdx + splitString.length(), rule.length()-1).split(", "); // -1 to remove full stop

        for (String subBag : allowedSubBags) {
            if (subBag.startsWith("no other")) {
                // Handle no other
                bagRules.put(bagColor, null);
            } else {
                final int countSplitIdx = subBag.indexOf(' ');
                final int subBagCount = Integer.parseInt(subBag.substring(0, countSplitIdx));

                final int subBagColorSplitIdx = subBag.indexOf(" bag");
                final String subBagColor = subBag.substring(countSplitIdx+1, subBagColorSplitIdx);

                if (!bagRules.containsKey(bagColor)) {
                    bagRules.put(bagColor, new ArrayList<>());
                }

                List<String> listOfBagRules = bagRules.get(bagColor);
                listOfBagRules.add(subBagColor);

                // PART 2
                bagCounts.put(bagColor.concat("-").concat(subBagColor), subBagCount);

            }
        }
    }

    // Only for PART 1
    private static boolean findMyBag(String bagColor, String myBag) {
        if (bagColor.equals(myBag)) return true;
        if (bagRules.get(bagColor) == null) return false;

        List<String> listOfSubBags = bagRules.get(bagColor);
        for (String subBag : listOfSubBags) {
            if (findMyBag(subBag, myBag)) return true;
        }

        return false;
    }

    // Only for PART 2
    private static int computeTotalSubBags(String bagColor) {
        List<String> subBags = bagRules.get(bagColor);
        int subTotal = 1;

        if (subBags == null) return subTotal;

        for (String subBag : subBags) {
            String lookUpString = bagColor.concat("-").concat(subBag);
            int count = bagCounts.get(lookUpString);
            subTotal += count * computeTotalSubBags(subBag);
        }
        return subTotal;
    }
}
