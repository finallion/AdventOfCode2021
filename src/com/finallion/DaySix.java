package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DaySix {

    public static void daySix() {
        String path = Main.getPath("Six");

        List<Integer> ages = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            String numbers = scanner.next();
            String[] splitted = numbers.split(",");
            for (String i : splitted) {
                ages.add(Integer.parseInt(i));
            }


        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + path);
        }


        //increasePopulationRecursive(ages, 80);
        //System.out.println("The result of Day Six Part One is " + ages.size());
        System.out.println("The result of Day Six Part Two is " + increasePopulation(ages, 256));

    }

    private static Long increasePopulation(List<Integer> ages, int days) {
        long[] cycle = new long[9];
        // fill each "age" with number of fishes
        ages.forEach(integer -> cycle[integer]++);

        // cycle through days
        for (int i = 0; i < days; i++) {
            long newborn = cycle[0];
            // shift each number one down (= fish have one day less to reproduce) -> System.arraycopy might be nicer
            for (int ii = 0; ii < cycle.length - 1; ii++) {
                cycle[ii] = cycle[ii + 1];
            }
            // add all renewed fish from 0 to age 6
            cycle[6] = newborn + cycle[6];
            // set newborn to age 8
            cycle[cycle.length - 1] = newborn;
        }

        return Arrays.stream(cycle).sum();


    }

    // recursive counting method, runs out of memory pretty fast
    private static List<Integer> increasePopulationRecursive(List<Integer> ages, int days) {
        if (days == 0) {
            return ages;
        }

        int newborn = 0;
        for (int i = 0; i < ages.size(); i++) {
            if (ages.get(i) == 0) {
                newborn++;
                ages.set(i, 6);
            } else {
                ages.set(i, ages.get(i) - 1);
            }
        }

        for (int i = 0; i < newborn; i++) {
            ages.add(8);
        }


        return increasePopulationRecursive(ages, days - 1);

    }

}
