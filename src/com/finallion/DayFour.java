package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DayFour {

    public static void dayFour() {
        String path = Main.getPath("Four");
        List<Integer> result = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(path))) {
            numbers = Arrays.stream(scanner.nextLine().split(",")).toList().stream().map(Integer::parseInt).toList();

            while (scanner.hasNext()) {
                int value = Integer.parseInt(scanner.next());
                result.add(value);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + path);
        }

        List<int[][]> matrices = createMatrices(result);
        playBingo(numbers, matrices);

    }

    private static void playBingo(List<Integer> numbers, List<int[][]> matrices) {
        for (Integer draw : numbers) {

            for (int[][] matrix : matrices) {
                for (int i = 0; i < 5; i++) {
                    for (int ii = 0; ii < 5; ii++) {

                        if (matrix[i][ii] == draw) {
                            matrix[i][ii] = -1;
                        }

                        if (checkRow(matrix) || checkColumn(matrix)) {
                            printMatrix(matrix);
                            int result = calculateRemainding(matrix);
                            System.out.println("The result of Day Three Part One is: " + result * draw);
                            return;
                        }
                    }
                }
            }


        }
    }

    private static int calculateRemainding(int[][] matrix) {
        int result = 0;
        for (int i = 0; i < 5; i++) {
            for (int ii = 0; ii < 5; ii++) {
                if (matrix[i][ii] != -1) {
                    result += matrix[i][ii];
                }

            }
        }

        return result;
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < 5; i++) {
            for (int ii = 0; ii < 5; ii++) {
                System.out.print(matrix[i][ii] + " ");
            }
            System.out.println("");
        }
    }

    private static boolean checkRow(int[][] matrix) {
        boolean checker = true;

        for (int i = 0; i < 5; i++) {
            checker = true;
            for (int ii = 0; ii < 5; ii++) {
                if (matrix[i][ii] != -1) {
                    checker = false;
                    break;
                }
            }
        }

        return checker;

    }

    private static boolean checkColumn(int[][] matrix) {
        boolean checker = true;

        for (int i = 0; i < 5; i++) {
            checker = true;
            for (int ii = 0; ii < 5; ii++) {
                if (matrix[ii][i] != -1) {
                    checker = false;
                    break;
                }
            }
        }

        return checker;
    }



    private static List<int[][]> createMatrices(List<Integer> numbers) {
        List<int[][]> matrices = new ArrayList<>();
        int matricesNumber = numbers.size() / 25;
        int index = 0;

        for (int i = 0; i < matricesNumber; i++) {
            int[][] matrix = new int[5][5];
            for (int ii = 0; ii < 5; ii++) {
                for (int iii = 0; iii < 5; iii++) {
                    matrix[ii][iii] = numbers.get(index);
                    index++;
                }
            }
            matrices.add(matrix);
        }

        return matrices;
    }

}
