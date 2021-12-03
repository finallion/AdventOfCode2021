package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayOne {

    public static List<Integer> readIntegerFile(String path) {
        List<Integer> result = new ArrayList<>();
        File file = new File(path);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                result.add(Integer.valueOf(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + path);
        }

        return result;
    }

    public static void dayOne() {
        List<Integer> numbers = readIntegerFile(Main.getPath("One"));
        int counter = 0;

        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) > numbers.get(i - 1)) {
                counter++;
            }
        }

        System.out.println("The result of Day One Part One: " + counter);
        /*

        ------------PART TWO-----------------

         */

        int windowOne;
        int windowTwo;
        int countIncrease = 0;
        int[] window = new int[]{numbers.get(0), numbers.get(1), numbers.get(2), numbers.get(3)};

        for (int i = 4; i < numbers.size(); i++) {
            windowOne = window[0] + window[1] + window[2];
            windowTwo = window[1] + window[2] + window[3];

            //System.out.println("Window One: " + window[0] + ", " + window[1] + ", " + window[2] + ": " + windowOne);
            //System.out.println("Window Two: " + window[1] + ", " + window[2] + ", " + window[3] + ": " + windowTwo);
            if (windowOne < windowTwo) {
                countIncrease++;
            }

            window[0] = window[1];
            window[1] = window[2];
            window[2] = window[3];
            window[3] = numbers.get(i);
        }

        // last iteration
        windowOne = window[0] + window[1] + window[2];
        windowTwo = window[1] + window[2] + window[3];
        if (windowOne < windowTwo) {
            countIncrease++;
        }

        System.out.println("The result of Day One Part Two: " + countIncrease);

    }



}
