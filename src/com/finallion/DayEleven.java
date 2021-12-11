package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayEleven {
    private static List<Main.Pair> offsets = new ArrayList<>();
    private static int flashes = 0;
    private static int stepFlashes = 0;
    private static boolean isSynchronized = false;

    public static void dayEleven() {
        String path = Main.getPath("Eleven");
        int[][] matrix = new int[10][10];

        try (Scanner scanner = new Scanner(new File(path))) {
            int row = 0;
            while (scanner.hasNextLine()) {
                String[] numbers = scanner.nextLine().trim().split("");
                for (int i = 0; i < numbers.length; i++) {
                    matrix[row][i] = Integer.parseInt(numbers[i]);
                }
                row++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*
        get neighbors in matrix
        X X X
        X O X
        X X X
         */
        offsets.add(new Main.Pair(1, 0));
        offsets.add(new Main.Pair(-1, 0));
        offsets.add(new Main.Pair(0, 1));
        offsets.add(new Main.Pair(0, -1));
        offsets.add(new Main.Pair(1, 1));
        offsets.add(new Main.Pair(1, -1));
        offsets.add(new Main.Pair(-1, 1));
        offsets.add(new Main.Pair(-1, -1));


        // matrix changes in partOne, comment out to run partTwo
        partOne(matrix, 100);
        partTwo(matrix);
    }

    private static void increaseByOne(int row, int col, int[][] matrix) {
        // don't update octopus that flashed
        if (matrix[row][col] != -1) {
            matrix[row][col] += 1;
        }
    }

    private static boolean isInGrid(int row, int col, int[][] matrix) {
        // checks if location is in bound
        if (row < 0 || row > matrix.length - 1) {
            return false;
        }

        if (col < 0 || col > matrix[0].length - 1) {
            return false;
        }

        return true;
    }

    private static boolean containsFlash(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int ii = 0; ii < matrix[0].length; ii++) {
                if (matrix[i][ii] > 9) {
                    return true;
                }

            }
        }
        return false;
    }

    private static void updateNeighbors(int row, int col, int[][] matrix) {
        for (Main.Pair offset : offsets) {
            int newRow = offset.getX() + row;
            int newCol = offset.getY() + col;
            if (isInGrid(newRow, newCol, matrix)) {
                increaseByOne(newRow, newCol, matrix);
            }
        }
    }

    private static void doFlash(int[][] matrix) {
        // count flashes in each step (it may be called multiple times each step)
        int roundFlashCounter = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int ii = 0; ii < matrix[0].length; ii++) {
                if (matrix[i][ii] > 9) {
                    updateNeighbors(i, ii, matrix);
                    // mark the ones that flashed this round
                    matrix[i][ii] = -1;
                    roundFlashCounter++;
                }
            }
        }

        // counts flashed for each step
        stepFlashes += roundFlashCounter;
        if (stepFlashes == 100) {
            isSynchronized = true;
        }


        flashes += roundFlashCounter;
    }

    private static void setToZero(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int ii = 0; ii < matrix[0].length; ii++) {
                if (matrix[i][ii] == -1) {
                    matrix[i][ii] = 0;
                }
            }
        }
    }


    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int ii = 0; ii < matrix[0].length; ii++) {
                System.out.print(matrix[i][ii] + " ");
            }
            System.out.println();
        }
        System.out.println("--------------");

    }

    private static void partOne(int[][] matrix, int rounds) {
        for (int i = 0; i < rounds; i++) {
            for (int ii = 0; ii < matrix.length; ii++) {
                for (int iii = 0; iii < matrix[0].length; iii++) {
                    // 1. increase every cell by one
                    increaseByOne(ii, iii, matrix);
                }
            }
            // 2. all cells > 9 flash, update neighbors. Repeat until there are no more > 9s
            while (containsFlash(matrix)) {
                doFlash(matrix);
            }
            // 3. set all marked cells (-1) to 0
            setToZero(matrix);
            //printMatrix(matrix);
        }

        System.out.println("The result of Day Eleven Part One is: " + flashes);

    }

    private static void partTwo(int[][] matrix) {
        int round = 1;
        while (!isSynchronized) {
            stepFlashes = 0;
            for (int i = 0; i < matrix.length; i++) {
                for (int ii = 0; ii < matrix[0].length; ii++) {
                    // 1. increase every cell by one
                    increaseByOne(i, ii, matrix);
                }
            }
            // 2. all cells > 9 flash, update neighbors. Repeat until there are no more > 9s
            while (containsFlash(matrix)) {
                doFlash(matrix);
            }
            // 3. set all marked cells (-1) to 0
            setToZero(matrix);
            //printMatrix(matrix);
            round++;
        }
        System.out.println("The result of Day Eleven Part Two is: " + (round - 1));
    }
}
