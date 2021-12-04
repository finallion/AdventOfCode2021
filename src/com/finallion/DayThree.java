package com.finallion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DayThree {

    public static void dayThree() throws IOException {
        String path = Main.getPath("Three");
        List<String> binary = Files.lines(Paths.get(path)).collect(Collectors.toList());

        getGammaAndEpsilon(binary);

    }

    private static void getGammaAndEpsilon(List<String> binary) {
        int[] counter = new int[binary.get(0).length()];

        for (String s : binary) {
            for (int i = 0; i < binary.get(0).length(); i++) {
                if (s.charAt(i) == '0') {
                    counter[i] -= 1;
                } else {
                    counter[i] += 1;
                }
            }

        }

        String gamma = "";
        String epsilon = "";
        for (Integer integer : counter) {
            gamma += integer < 0 ? 0 : 1;
            epsilon += integer < 0 ? 1 : 0;
        }


        System.out.println(gamma);
        System.out.println(epsilon);
        System.out.println("The result of Day Three Part One: " + Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2));
        System.out.println("The result of Day Three Part Two: " + getOxygenAndCO2(binary, "gamma") * getOxygenAndCO2(binary, "epsilon"));

    }

    private static int getOxygenAndCO2(List<String> binary, String value) {

        for (int i = 0; i < binary.get(0).length(); i++) {
            int balance = 0;
            for (String s : binary) {
                if (s.charAt(i) == '1') {
                    balance++;
                } else {
                    balance--;
                }

            }

            // collect all matches from round
            List<String> matches = new ArrayList<>();
            for (String s : binary) {
                if (balance >= 0) {
                    if ((value.equals("gamma") && s.charAt(i) == '1') || (value.equals("epsilon") && s.charAt(i) == '0')) {
                        matches.add(s);
                    }
                } else {
                    if ((value.equals("gamma") && s.charAt(i) == '0') || (value.equals("epsilon") && s.charAt(i) == '1')) {
                        matches.add(s);
                    }
                }
            }

            // iterate again through matches (for-loop stop point is binary.get(0).length)
            binary = matches;
            if (binary.size() == 1) {
                return Integer.parseInt(binary.get(0), 2);
            }
        }
        return 0;
    }
}
