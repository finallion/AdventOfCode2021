package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayTwo {

    public static void dayTwo() {
        int forward = 0;
        int depth = 0;
        int aim = 0;
        String path = Main.getPath("Two");
        File file = new File(path);

        /*
        ------------PART ONE-----------------
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String command = scanner.next();
                int movement = Integer.parseInt(scanner.next());

                switch (command) {
                    case "forward" -> forward += movement;
                    case "up" -> depth -= movement;
                    case "down" -> depth += movement;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + path);
        }

        System.out.println("The result of Day Two Part One: " + forward * depth);

         */

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String command = scanner.next();
                int movement = Integer.parseInt(scanner.next());

                switch (command) {
                    case "forward" -> {
                        forward += movement;
                        depth += aim * movement;
                    }

                    case "up" -> aim -= movement;
                    case "down" -> aim += movement;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + path);
        }

        System.out.println("The result of Day Two Part One: " + forward * depth);

    }
}
