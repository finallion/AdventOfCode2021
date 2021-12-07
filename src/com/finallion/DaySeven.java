package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DaySeven {

    public static void daySeven() {
        String path = Main.getPath("Seven");

        List<Integer> movements = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            String numbers = scanner.next();
            String[] splitted = numbers.split(",");
            for (String i : splitted) {
                movements.add(Integer.parseInt(i));
            }


        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + path);
        }


        int max = movements.stream().max(Integer::compareTo).get();

        int optimalFuelUse = Integer.MAX_VALUE;
        for (int i = 0; i < max; i++) {
            int temp = getFuelUse(movements, i);
            if (temp < optimalFuelUse) {
                optimalFuelUse = temp;
            }

        }

        //System.out.println("The solution for Day Seven Part One is: " + optimalFuelUse);
        System.out.println("The solution for Day Seven Part Two is: " + optimalFuelUse);
    }


    private static int getFuelUse(List<Integer> movements, int position) {
        int fuel = 0;
        int distance;
        for (Integer integer : movements) {
            if (integer < position) {
                /*
                PART ONE:
                fuel += position - integer;
                 */
                distance = position - integer;
                for (int i = 1; i <= distance; i++) {
                    fuel += i;
                }
            } else {
                /*
                PART ONE:
                fuel += Math.abs(integer - position);
                 */
                distance = Math.abs(integer - position);
                for (int i = 1; i <= distance; i++) {
                    fuel += i;
                }
            }
        }

        return fuel;

    }


}
