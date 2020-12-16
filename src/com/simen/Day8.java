package com.simen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Day8 {

    private static List<String> instructions = new ArrayList<>();
    private static List<Integer> idxs = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
            stream.forEach(instructions::add);
        }

        for (int i = 0; i < instructions.size(); i++) {
            if (instructions.get(i).startsWith("nop") || instructions.get(i).startsWith("jmp")) idxs.add(i);
        }

        for (int idx : idxs) {
            if (runProgram(idx)) return;
        }
    }

    private static boolean runProgram(int idxToSwap) {
        int acc = 0, currIdx = 0;
        Set<Integer> visitedInstructions = new HashSet<>();

        while (currIdx < instructions.size()) {
            String instruction = instructions.get(currIdx);
            String opcode = instruction.substring(0, 3);
            int argument = Integer.parseInt(instruction.substring(instruction.indexOf(' ') + 1));

            if (visitedInstructions.contains(currIdx)) {
                return false;
            } else {
                visitedInstructions.add(currIdx);
            }

            if (currIdx == idxToSwap) {
                if (opcode.equals("nop")) opcode = "jmp";
                if (opcode.equals("jmp")) opcode = "nop";
            }

            switch (opcode) {
                case "nop":
                    currIdx++;
                    break;
                case "acc":
                    acc += argument;
                    currIdx++;
                    break;
                case "jmp":
                    currIdx += argument;
                    break;
            }
        }
        System.out.println(acc);
        return true;
    }

}