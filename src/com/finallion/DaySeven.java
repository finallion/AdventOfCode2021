package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DaySeven {
    public static void daySevenBetter() {
        String path = Main.getPath("Seven");
        List<Integer> movements = new ArrayList<>();


        try (Scanner scanner = new Scanner(new File(path))) {
            movements = Arrays.stream(scanner.nextLine().trim().split(",")).map(Integer::parseInt).toList();
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + path);
        }
        movements = movements.stream().sorted().collect(Collectors.toList());

        // PART ONE
        int median = movements.get(movements.size() / 2);

        int fuelPartOne = 0;
        for (Integer integer : movements) {
            fuelPartOne += Math.abs(integer - median);
        }

        System.out.println("The solution for Day Seven Part One is: " + fuelPartOne);

        // PART TWO
        int average = movements.stream().mapToInt(Integer::intValue).sum() / movements.size();

        int fuelPartTwo = 0;
        for (Integer integer : movements) {
            int distance = Math.abs(integer - average);
            fuelPartTwo += distance * (distance + 1) / 2;
        }

        System.out.println("The solution for Day Seven Part Two is: " + fuelPartTwo);

    }

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
        // Arrays.stream(scanner.nextLine().trim().split(",")).mapToInt(Integer::parseInt).toArray();


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
