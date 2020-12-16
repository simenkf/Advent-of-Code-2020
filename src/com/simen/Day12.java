package com.simen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.lang.Math.abs;

public class Day12 {

    static int x = 0;
    static int y = 0;
    static int dir = 90;

    static int waypointX = 10;
    static int waypointY = 1;

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
            stream.forEach(Day12::part2);
        }
        System.out.println(abs(x) + abs(y));
    }


    public static void part2(String instruction) {
        char action = instruction.charAt(0);
        int value = Integer.parseInt(instruction.substring(1));

        switch (action) {
            case 'N':
                waypointY += value;
                break;
            case 'S':
                waypointY -= value;
                break;
            case 'E':
                waypointX += value;
                break;
            case 'W':
                waypointX -= value;
                break;
            case 'F':
                moveToWayPoint(value);
                break;
            case 'L':
                rotateLeft(value);
                break;
            case 'R':
                rotateRight(value);
        }
    }

    public static void part1(String instruction) {
        char action = instruction.charAt(0);
        int value = Integer.parseInt(instruction.substring(1));

        switch (action) {
            case 'N':
                y += value;
                break;
            case 'S':
                y -= value;
                break;
            case 'E':
                x += value;
                break;
            case 'W':
                x -= value;
                break;
            case 'F':
                moveForward(value);
                break;
            case 'L':
                dir = (dir + 360 - value) % 360;
                break;
            case 'R':
                dir = (dir + 360 + value) % 360;
        }
    }

    private static void rotateLeft(int value) {
        int relDistX = waypointX - x;
        int relDistY = waypointY - y;

        if (value == 90) {
            waypointX = x - relDistY;
            waypointY = y + relDistX;
        } else if (value == 180) {
            rotateLeft(90);
            rotateLeft(90);
        } else if (value == 270) {
            rotateLeft(90);
            rotateLeft(90);
            rotateLeft(90);
        }
    }

    private static void rotateRight(int value) {
        if (value == 90) rotateLeft(270);
        else if (value == 180) rotateLeft(180);
        else if (value == 270) rotateLeft(90);
    }

    private static void moveForward(int value) {
        if (dir == 0) y += value;
        else if (dir == 90) x += value;
        else if (dir == 180) y -= value;
        else if (dir == 270) x -= value;
    }

    private static void moveToWayPoint(int numberOfTimes) {
        for (int i = 0; i < numberOfTimes; i++) {
            int relDistX = waypointX - x;
            int relDistY = waypointY - y;
            x = waypointX;
            y = waypointY;
            waypointX += relDistX;
            waypointY += relDistY;
        }
    }
}
