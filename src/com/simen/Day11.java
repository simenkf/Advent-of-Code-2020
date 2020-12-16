package com.simen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Day11 {
    private static List<List<Character>> seats = new ArrayList<>();
    private static int ROWS;
    private static int COLS;

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
            stream.forEach(Day11::processInputLine);
            ROWS = seats.size();
            COLS = seats.get(0).size();
        }

        int iter = 0;
        while (simulate()) {
            System.out.println("After iteration: " + ++iter);
        }

        System.out.println(seats.stream().map(row -> Collections.frequency(row, '#')).reduce(0, (a, b) -> a + b));
    }

    private static boolean simulate() {
        List<List<Character>> copySeats = new ArrayList<>();
        for (List<Character> l : seats) copySeats.add(new ArrayList<>(l));

        boolean appliedChanges = false;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                switch (seats.get(i).get(j)) {
                    case '.':
                        break;
                    case 'L':
                        if (countOccupiedNeighboursPart2(i, j) == 0) {
                            copySeats.get(i).set(j, '#');
                            appliedChanges = true;
                        }
                        break;
                    case '#':
                        if (countOccupiedNeighboursPart2(i, j) >= 5) {
                            copySeats.get(i).set(j, 'L');
                            appliedChanges = true;
                        }
                }
            }
        }
        seats = copySeats;
        return appliedChanges;
    }

    // PART 2
    private static int countOccupiedNeighboursPart2(int i, int j) {
        int total = 0;
        int curri = i;
        int currj = j;

        // up
        curri = i - 1;
        while (curri >= 0) {
            if (seats.get(curri).get(j) == '#') {
                total++;
                break;
            } else if (seats.get(curri).get(j) == 'L') {
                break;
            }
            curri--;
        }

        // down
        curri = i + 1;
        while (curri < ROWS) {
            if (seats.get(curri).get(j) == '#') {
                total++;
                break;
            } else if (seats.get(curri).get(j) == 'L') {
                break;
            }
            curri++;
        }

        // left
        currj = j - 1;
        while (currj >= 0) {
            if (seats.get(i).get(currj) == '#') {
                total++;
                break;
            } else if (seats.get(i).get(currj) == 'L') {
                break;
            }
            currj--;
        }

        // right
        currj = j + 1;
        while (currj < COLS) {
            if (seats.get(i).get(currj) == '#') {
                total++;
                break;
            } else if (seats.get(i).get(currj) == 'L') {
                break;
            }
            currj++;
        }

        // up left
        curri = i - 1;
        currj = j - 1;
        while (curri >= 0 && currj >= 0) {
            if (seats.get(curri).get(currj) == '#') {
                total++;
                break;
            } else if (seats.get(curri).get(currj) == 'L') {
                break;
            }
            curri--; currj--;
        }

        // up right
        curri = i - 1;
        currj = j + 1;
        while (curri >= 0 && currj < COLS) {
            if (seats.get(curri).get(currj) == '#') {
                total++;
                break;
            } else if (seats.get(curri).get(currj) == 'L') {
                break;
            }
            curri--; currj++;
        }

        // down right
        curri = i + 1;
        currj = j + 1;
        while (curri < ROWS && currj < COLS) {
            if (seats.get(curri).get(currj) == '#') {
                total++;
                break;
            } else if (seats.get(curri).get(currj) == 'L') {
                break;
            }
            curri++; currj++;
        }

        // down left
        curri = i + 1;
        currj = j - 1;
        while (curri < ROWS && currj >= 0) {
            if (seats.get(curri).get(currj) == '#') {
                total++;
                break;
            } else if (seats.get(curri).get(currj) == 'L') {
                break;
            }
            curri++; currj--;
        }


        return total;
    }

    // PART 1
    private static int countOccupiedNeighbours(int i, int j) {
        int total = 0;
        for (int deltai = -1; deltai <= 1; deltai++) {
            for (int deltaj = -1; deltaj <= 1; deltaj++) {
                if (deltai == 0 && deltaj == 0) continue;
                if (i+deltai >= 0 && i+deltai < ROWS &&
                    j+deltaj >= 0 && j+deltaj < COLS &&
                    seats.get(i + deltai).get(j + deltaj) == '#') {
                    total++;
                }
            }
        }
        return total;
    }

    public static void processInputLine(String s) {
        List<Character> row = new ArrayList<>();
        for (char c : s.toCharArray()) row.add(c);
        seats.add(row);
    }
}
