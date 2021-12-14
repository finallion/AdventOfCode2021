package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayFourteen {

    public static void dayFourteenBetter() {
        String path = Main.getPath("Fourteen");

        String startTemplate = "";

        Map<String, String> grammar = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            startTemplate = scanner.nextLine();
            // skip empty line due to input structure, now follows the grammar of the polymerisation process
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String instruction = scanner.nextLine();
                grammar.put(instruction.split(" -> ")[0], instruction.split(" -> ")[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Map<String, Long> pairValues = new HashMap<>();

        // initialize map with pairs from startTemplate (there are string.length - 1 pairs in each string)
        for (int i = 0; i < startTemplate.length() - 1; i++) {
            pairValues.merge(startTemplate.substring(i, i + 2), 1L, Long::sum);
        }

        // partOne
        System.out.println("The result of Day Fourteen Part One is: " + solve(pairValues, grammar, 10));
        // partTwo
        System.out.println("The result of Day Fourteen Part Two is: " + solve(pairValues, grammar, 40));
    }

    private static long solve(Map<String, Long> pairValues, Map<String, String> grammar, int rounds) {
        for (int i = 0; i < rounds; i++) {
            Map<String, Long> temp = new HashMap<>();
            for (String key : pairValues.keySet()) {
                // for every pair, get the two result pairs (NN -> NC, CN)
                // if the resulting pairs is already in the map, merge its value (aka count how many identical pairs are in the map)
                temp.merge(key.charAt(0) + grammar.get(key), pairValues.get(key), Long::sum);
                temp.merge(grammar.get(key) + key.charAt(1), pairValues.get(key), Long::sum);
            }
            // set old map to the new map with all pairs
            pairValues = temp;
        }

        return calculateResult(pairValues) + 1;
    }



    private static Long calculateResult(Map<String, Long> pairValues) {
        Map<Character, Long> singleValue = new HashMap<>();

        // the map contains pairs, but the second char is also the first character of another pair
        // remember: NN -> NC - CN
        // collect the first char and its value in another map
        for (String key : pairValues.keySet()) {
            // merge is needed because too many keys would be identical -> merge these and sum their values up
            singleValue.merge(key.charAt(0), pairValues.get(key), Long::sum);
        }

        // stream through values to retrieve letter that occurs max and min
        return singleValue.values().stream().max(Long::compareTo).get() - singleValue.values().stream().min(Long::compareTo).get();
    }


}
