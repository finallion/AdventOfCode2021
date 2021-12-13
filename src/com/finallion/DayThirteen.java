package com.finallion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DayThirteen {

    public static void dayThirteen() {
        String path = Main.getPath("Thirteen");

        List<String> lines = new ArrayList<>();
        List<String> fold = new ArrayList<>();

        boolean instructionStart = false;

        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    instructionStart = true;
                    continue;
                }

                if (!instructionStart) {
                    lines.add(line);
                } else {
                    fold.add(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // WHY WOULD YOU MAKE THE FIRST VALUE THE COLUMN VALUE ADVENT OF CODE; WHY!!
        int howManyColumns = lines.stream().map(l -> l.split(",")[0]).mapToInt(Integer::parseInt).max().getAsInt() + 1;
        int howManyRows = lines.stream().map(l -> l.split(",")[1]).mapToInt(Integer::parseInt).max().getAsInt() + 1;

        char[][] matrix = new char[howManyRows][howManyColumns];
        initMatrix(matrix, lines);

        Map<Integer, Character> foldInstructions = getFoldInstructions(fold);

        partOne(matrix, foldInstructions);
        partTwo(matrix, foldInstructions);
    }

    private static Map<Integer, Character> getFoldInstructions(List<String> fold) {
        Map<Integer, Character> foldInstructions = new LinkedHashMap<>();
        for (String line : fold) {
            String xOrY = line.split("=")[0];
            foldInstructions.put(Integer.parseInt(line.split("=")[1]), xOrY.charAt(xOrY.length() - 1));
        }
        return foldInstructions;
    }

    private static char[][] initMatrix(char[][] matrix, List<String> lines) {
        for (String coord : lines) {
            int x = Integer.parseInt(coord.split(",")[1]);
            int y = Integer.parseInt(coord.split(",")[0]);
            for (int row = 0; row < matrix.length; row++) {
                for (int col = 0; col < matrix[0].length; col++) {
                    if (x == row && y == col) {
                        matrix[row][col] = '#';
                    }

                    if (matrix[row][col] != '#') {
                        matrix[row][col] = '.';
                    }
                }
            }
        }

        return matrix;
    }

    private static void printMatrix(char[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }

    private static char[][] splitCopyMatrixVertical(char[][] matrix, int x) {
        // create new matrix with same row value, but "folded" col value
        char[][] newMatrix = new char[matrix.length][x];
        // copy each value from the old matrix into the smaller new one, ignoring all the values on the soon-to-be folded side
        copyMatrix(matrix, newMatrix);

        // copy all values from the folded side, starting at column value = x
        // the value gets mirrored into the new matrix - therefore the counter counting from x down
        for (int row = 0; row < matrix.length; row++) {
            int colCounter = x;
            for (int col = x; col < matrix[0].length; col++) {
                if (matrix[row][col] == '#') {
                    newMatrix[row][colCounter] = '#';
                }
                colCounter--;
            }
        }
        return newMatrix;
    }

    private static char[][] splitCopyMatrixHorizontal(char[][] matrix, int y) {
        // create new matrix with same col value, but "folded" row value
        char[][] newMatrix = new char[y][matrix[0].length];
        // copy each value from the old matrix into the smaller new one, ignoring all the values on the soon-to-be folded side
        copyMatrix(matrix, newMatrix);

        // copy all values from the folded side, starting at row value = y
        // the value gets mirrored into the new matrix - therefore the counter counting from y down
        int rowCounter = y;
        for (int row = y; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] == '#') {
                    newMatrix[rowCounter][col] = '#';
                }
            }
            rowCounter--;
        }

        return newMatrix;
    }

    private static int countHashes(char[][] matrix) {
        int counter = 0;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] == '#') counter++;
            }
        }

        return counter;
    }

    private static char[][] copyMatrix(char[][] matrix, char[][] newMatrix) {
        for (int row = 0; row < newMatrix.length; row++) {
            for (int col = 0; col < newMatrix[0].length; col++) {
                newMatrix[row][col] = matrix[row][col];
            }
        }

        return newMatrix;
    }

    private static void partOne(char[][] matrix, Map<Integer, Character> foldIn) {
        Character value = foldIn.entrySet().stream().findFirst().get().getValue();
        Integer key = foldIn.entrySet().stream().findFirst().get().getKey();

        if (value == 'x') {
            matrix = splitCopyMatrixVertical(matrix, key);
        } else {
            matrix = splitCopyMatrixHorizontal(matrix, foldIn.get(key));
        }


        System.out.println("The result of Day Thirteen Part One is: " + countHashes(matrix));

    }

    private static void partTwo(char[][] matrix, Map<Integer, Character> foldIn) {
        for (Integer key : foldIn.keySet()) {
            if (foldIn.get(key) == 'x') {
                matrix = splitCopyMatrixVertical(matrix, key);
            } else {
                matrix = splitCopyMatrixHorizontal(matrix, key);
            }
        }

        System.out.println("The result of Day Thirteen Part Two is: ");
        printMatrix(matrix);

    }
}
