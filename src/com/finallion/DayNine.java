package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Predicate;

public class DayNine {

    public static void dayNine() {
        String path = Main.getPath("Nine");
        int[][] matrix = new int[100][100];

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

        partOne(matrix);
        partTwo(matrix);
    }

    private static void partOne(int[][] matrix) {
        List<Integer> minimums = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int ii = 0; ii < matrix[0].length; ii++) {
                if (isMinAdjacent(i, ii, matrix)) {
                    minimums.add(matrix[i][ii]);
                }
            }
        }
        System.out.println("The result for Day One Part One is: " + (minimums.stream().mapToInt(i -> i).sum() + minimums.size()));
    }

    private static boolean isMinAdjacent(int row, int column, int[][] matrix) {
        List<Integer> adjacentInt = new ArrayList<>();
        if (row - 1 >= 0) adjacentInt.add(matrix[row - 1][column]);
        if (row + 1 <= matrix.length - 1) adjacentInt.add(matrix[row + 1][column]);
        if (column - 1 >= 0) adjacentInt.add(matrix[row][column - 1]);
        if (column + 1 <= matrix[0].length - 1) adjacentInt.add(matrix[row][column + 1]);
        Collections.sort(adjacentInt);

        return adjacentInt.get(0) > matrix[row][column];
    }

    private static void locateBasin(int row, int column, int value, int[][] matrix, Map<Main.Pair, Integer> basin) {
        Map<Main.Pair, Integer> temp = new HashMap<>();
        // first: check if neighbor is in bounds, second: check if neighbor is higher than value, third: neighbor cant be 9
        if (row - 1 >= 0 && matrix[row - 1][column] > value && matrix[row - 1][column] != 9) {
            temp.putIfAbsent(new Main.Pair(row - 1, column), matrix[row - 1][column]);
        }
        if (row + 1 <= matrix.length - 1 && matrix[row + 1][column] > value && matrix[row + 1][column] != 9) {
            temp.putIfAbsent(new Main.Pair(row + 1, column), matrix[row + 1][column]);
        }
        if (column - 1 >= 0 && matrix[row][column - 1] > value && matrix[row][column - 1] != 9) {
            temp.putIfAbsent(new Main.Pair(row, column - 1), matrix[row][column - 1]);
        }
        if (column + 1 <= matrix[0].length - 1 && matrix[row][column + 1] > value && matrix[row][column + 1] != 9) {
            temp.putIfAbsent(new Main.Pair(row, column + 1), matrix[row][column + 1]);
        }

        // add all found higher neighbors to basin
        basin.putAll(temp);

        // check for each neighbor its neighbors
        for (Main.Pair pair : temp.keySet()) {
            locateBasin(pair.getX(), pair.getY(), temp.get(pair), matrix, basin);
        }
    }

    private static void partTwo(int[][] matrix) {
        List<Integer> basinSize = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int ii = 0; ii < matrix[0].length; ii++) {
                if (isMinAdjacent(i, ii, matrix)) {
                    // init map with lowest location
                    Map<Main.Pair, Integer> basin = new HashMap<>();
                    basin.put(new Main.Pair(i, ii), matrix[i][ii]);

                    // for every neighbor collect neighbors, that are one higher
                    locateBasin(i, ii, matrix[i][ii], matrix, basin);

                    basinSize.add(basin.size());
                }
            }
        }

        Collections.sort(basinSize, Collections.reverseOrder());
        System.out.println("The result of Day Nine Part Two is: " + basinSize.get(0) * basinSize.get(1) * basinSize.get(2));
    }
}
