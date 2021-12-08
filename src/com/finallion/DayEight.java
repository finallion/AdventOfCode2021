package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayEight {

    public static void dayEight() {
        String path = Main.getPath("Eight");
        System.out.println("The result for Day Eight Part One is: " + partOne(path));
        System.out.println("The result for Day Eight Part Two is: " + partTwo(path));
    }

    private static Map<Integer, String> mapLettersToDigits(String codes) {
        Map<Integer, String> stringToInt = new HashMap<>();
        String[] singleDigits = codes.trim().split(" ");

        // init map with known numbers
        for (String digit : singleDigits) {
            switch (digit.length()) {
                case 2 -> stringToInt.put(1, digit);
                case 3 -> stringToInt.put(7, digit);
                case 4 -> stringToInt.put(4, digit);
                case 7 -> stringToInt.put(8, digit);
            }
        }

        // add numbers found comparing by init-numbers
        for (String digit : singleDigits) {
            switch (digit.length()) {
                // get number 3 from number 1
                case 5 -> {
                    if (matchesNumber(digit, stringToInt.get(1), 2)) {
                        stringToInt.put(3, digit);
                    }
                }
                // get number 6 from number 1
                case 6 -> {
                    if (!matchesNumber(digit, stringToInt.get(1), 2)) {
                        stringToInt.put(6, digit);
                    }
                }
            }
        }

        // third round: get last numbers by added numbers
        for (String digit : singleDigits) {
            // find numbers 2 and 5 from 3 and 4
            if (digit.length() == 5 && !digit.equals(stringToInt.get(3))) {
                if (matchesNumber(digit, stringToInt.get(3), 4) && matchesNumber(digit, stringToInt.get(4), 2)) {
                    stringToInt.put(2, digit);
                } else {
                    stringToInt.put(5, digit);
                }
            }

            // find numbers 0 and 9 from 4
            if (digit.length() == 6  && !digit.equals(stringToInt.get(6))) {
                if (!matchesNumber(digit, stringToInt.get(4), 4)) {
                    stringToInt.put(0, digit);
                } else {
                    stringToInt.put(9, digit);
                }
            }
        }
        return stringToInt;
    }


    private static boolean matchesNumber(String one, String two, int matches) {
        int counter = 0;
        for (int i = 0; i < one.length(); i++) {
            for (int ii = 0; ii < two.length(); ii++) {
                if (one.charAt(i) == two.charAt(ii)) {
                    counter++;
                }
            }
        }
        return counter == matches;
    }


    private static int partTwo(String path) {
        List<String> values = new ArrayList<>();
        List<String> codes = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                values.add((line.trim().split("\\| ")[1]));
                codes.add((line.trim().split(" \\| ")[0]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + path);
        }

        int counter = 0;
        // for every value line, split it into single strings ("cfgab")
        for (int i = 0; i < values.size(); i++) {
            String[] singleDigits = values.get(i).trim().split(" ");
            Map<Integer, String> stringToInt = mapLettersToDigits(codes.get(i)); // 0 = "abcdef", 1 = "cf" ...
            String result = "";

            // for each string, search through map if it contains that string
            for (String singleDigit : singleDigits) {
                for (Integer number : stringToInt.keySet()) {

                    // if length of mapped number is higher than the searched value, skip
                    if (stringToInt.get(number).length() > singleDigit.length()) {
                        continue;
                    }

                    if (matchesNumber(singleDigit, stringToInt.get(number), singleDigit.length())) {
                        result += number;
                        break;
                    }
                }
            }
            counter += Integer.parseInt(result);
        }
        return counter;
    }


    private static int partOne(String path) {
        List<String> values = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                values.addAll(Arrays.stream(scanner.nextLine().trim().split("\\| ")[1].split(" ")).toList());
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + path);
        }

        int counter = 0;
        for (String s : values) {
            // get digits: 8, 4, 7, 1
            if (s.length() == 7 || s.length() == 4 || s.length() == 3 || s.length() == 2) {
                counter++;
            }
        }

        return counter;
    }
}
