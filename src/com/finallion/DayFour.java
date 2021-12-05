package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
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
        Set<Integer> winners = new HashSet<>();

        // iterate through all draw
        // for each draw iterate through every matrix and set the drawn number
        // if then the matrix wins, put it into the set
        // if the set reaches 99 (there are 100 matrices), print the last remaining.
        for (Integer draw : numbers) {
            for (int i = 0; i < matrices.size(); i++) {
                if (winners.contains(i)) {
                    continue;
                }

                for (int ii = 0; ii < 5; ii++) {
                    for (int iii = 0; iii < 5; iii++) {
                        if (winners.size() == 99) {
                            printMatrix(matrices.get(i));
                            int result = calculateRemaining(matrices.get(i));
                            System.out.println("Draw : " + draw);
                            System.out.println("The result of Day Three Part Two is: " + result * draw);
                            return;
                        }


                        // add wining matrix to blacklist
                        if (checkRow(matrices.get(i)) || checkColumn(matrices.get(i))) {
                            winners.add(i);
                        }

                        // set drawn number to matrix
                        if (matrices.get(i)[ii][iii] == draw) {
                            matrices.get(i)[ii][iii] = -1;
                        }


                    }
                }
            }
        }



        /*
        ------ PART ONE -----

        for (Integer draw : numbers) {
            for (int[][] matrix : matrices) {
                for (int i = 0; i < 5; i++) {
                    for (int ii = 0; ii < 5; ii++) {
                        if (matrix[i][ii] == draw) {
                            matrix[i][ii] = -1;
                        }
                        if (checkRow(matrix) || checkColumn(matrix)) {
                            printMatrix(matrix);
                            int result = calculateRemaining(matrix);
                            System.out.println("The result of Day Three Part One is: " + result * draw);
                            return;
                        }
                    }
                }
            }
        }

         */
    }


    // calculates all remaining fields that are not yet drawn
    private static int calculateRemaining(int[][] matrix) {
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
        for (int i = 0; i < 5; i++) {
            int counter = 0;
            for (int ii = 0; ii < 5; ii++) {
                if (matrix[ii][i] == -1) {
                    counter++;
                }
            }

            if (counter == 5) {
                return true;
            }

        }

        return false;
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
