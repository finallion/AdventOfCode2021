package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class DayFive {

    public static void dayFive() {
        String path = Main.getPath("Five");
        Map<Pair, Pair> pairs = new HashMap<>();
        int[][] diagram = new int[1000][1000];

        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // 1,2 -> 3,4
                String[] stringPairs = line.split(" -> "); // [(1,2),(3,4)]
                String[] xyOne = stringPairs[0].split(","); // [1,2]
                String[] xyTwo = stringPairs[1].split(","); // [3,4]
                pairs.put(new Pair(Integer.parseInt(xyOne[0]), Integer.parseInt(xyOne[1])), new Pair(Integer.parseInt(xyTwo[0]), Integer.parseInt(xyTwo[1])));

            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + path);
        }

        for (Pair keys : pairs.keySet()) {
            int keyX = keys.getX();
            int valueX = pairs.get(keys).getX();
            int keyY = keys.getY();
            int valueY = pairs.get(keys).getY();

            if (Math.abs(keyX - valueX) == Math.abs(keyY - valueY)) {
                fillMatrixDiagonal(diagram, keyX, keyY, valueX, valueY);
            }
            if (keyX == valueX) {
                fillMatrixY(diagram, keyX, keyY, valueY);
            }
            if (keyY == valueY) {
                fillMatrixX(diagram, keyY, keyX, valueX);
            }
        }

        int threshold = 1;
        // PART ONE: int threshold = 0;
        System.out.println("The result of Day Five Part One is: " + traverseMatrix(diagram, threshold));
    }

    private static int traverseMatrix(int[][] matrix, int threshold) {
        int counter = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int ii = 0; ii < matrix.length; ii++) {
                if (matrix[i][ii] > threshold) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private static void fillMatrixDiagonal(int[][] matrix, int xOne, int yOne, int xTwo, int yTwo) {
        if (xOne > xTwo) {
            // go towards negative x
            if (yOne > yTwo) {
                // go towards negative y
                for (int i = 0; i <= xOne - xTwo; i++) {
                    matrix[xOne - i][yOne - i] += 1;
                }
            } else {
                // go towards positive y
                for (int i = 0; i <= xOne - xTwo; i++) {
                    matrix[xOne - i][yOne + i] += 1;
                }
            }
        } else {
            // go towards positive x
            if (yOne > yTwo) {
                // go towards negative y
                for (int i = 0; i <= xTwo - xOne; i++) {
                    matrix[xOne + i][yOne - i] += 1;
                }
            } else {
                // go towards positive y
                for (int i = 0; i <= xTwo - xOne; i++) {
                    matrix[xOne + i][yOne + i] += 1;
                }
            }
        }
    }

    private static void fillMatrixY(int[][] matrix, int xOne, int yOne, int yTwo) {
        if (yOne > yTwo) {
            for (int i = 0; i <= yOne - yTwo; i++) {
                matrix[xOne][yOne - i] += 1;
            }
        } else {
            for (int i = 0; i <= yTwo - yOne; i++) {
                matrix[xOne][yOne + i] += 1;
            }
        }
    }

    private static void fillMatrixX(int[][] matrix, int yOne, int xOne, int xTwo) {
        if (xOne > xTwo) {
            for (int i = 0; i <= xOne - xTwo; i++) {
                matrix[xOne - i][yOne] += 1;
            }
        } else {
            for (int i = 0; i <= xTwo - xOne; i++) {
                matrix[xOne + i][yOne] += 1;
            }
        }
    }


    private static class Pair {
        int xNode;
        int yNode;

        public Pair(int x, int y) {
            this.xNode = x;
            this.yNode = y;
        }

        public int getX() {
            return xNode;
        }

        public int getY() {
            return yNode;
        }

        @Override
        public String toString() {
            return "X: " + getX() + ", Y: " + getY();
        }
    }

}

