package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayThree {
    private static List<String> binary = new ArrayList<>();

    public static void dayThree() {
        String path = Main.getPath("Three");
        File file = new File(path);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                binary.add(scanner.nextLine());

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }

        countBits();

    }

    private static void countBits() {
        int[] counter = new int[12];

        for (String s : binary) {
            for (int i = 0; i < 12; i++) {
                if (s.charAt(i) == '0') {
                    counter[i] -= 1;
                } else {
                    counter[i] += 1;
                }
            }

        }

        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        for (Integer integer : counter) {
            if (integer < 0) {
                gamma.append(0);
                epsilon.append(1);
            } else {
                gamma.append(1);
                epsilon.append(0);
            }
        }

        int gammaInt = Integer.parseInt(gamma.toString(), 2);
        int epsilonInt = Integer.parseInt(epsilon.toString(), 2);
        System.out.println("Gamma: " + gammaInt);
        System.out.println("Epsilon: " + epsilonInt);
        System.out.println("The result of Day Three Part One: " + gammaInt * epsilonInt);

    }
}
