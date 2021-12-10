package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayTen {
    private static Map<String, String> pairs = new HashMap<>();

    public static void dayTen() {
        String path = Main.getPath("Ten");
        List<List<String>> input = new ArrayList<>();

        pairs.put("(", ")");
        pairs.put("[", "]");
        pairs.put("{", "}");
        pairs.put("<", ">");

        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                input.add(Arrays.stream(scanner.nextLine().split("")).toList());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        partOne(input);
        partTwo(input);
    }

    private static int countClosing(String s, boolean partOne) {
        return switch (s) {
            case ")" -> partOne ? 3 : 1;
            case "]" -> partOne ? 57 : 2;
            case "}" -> partOne ? 1197 : 3;
            case ">" -> partOne ? 25137 : 4;
            default -> 0;
        };
    }

    // could be improved by adapting code of partOne
    private static List<List<String>> removeCorruptedLines(List<List<String>> input) {
        List<List<String>> onlyIncomplete = new ArrayList<>();

        for (List<String> line : input) {
            Deque<String> stack = new ArrayDeque<>();
            boolean remove = false;

            for (String brace : line) {
                if (pairs.keySet().contains(brace)) {
                    stack.push(brace);
                } else {
                    if (pairs.get(stack.peek()).equals(brace)) {
                        stack.pop();
                    } else {
                        remove = true;
                        break;
                    }
                }
            }

            if (!remove) {
                onlyIncomplete.add(line);
            }
        }
        return onlyIncomplete;
    }

    private static long getAndCountMissingBraces(Deque<String> stack) {
        long counter = 0;
        for (String brace : stack) {
            counter *= 5;
            counter += countClosing(pairs.get(brace), false);
        }
        return counter;
    }

    private static void partTwo(List<List<String>> input) {
        input = removeCorruptedLines(input);
        List<Long> results = new ArrayList<>();

        for (List<String> line : input) {
            long counter = 0;
            Deque<String> stack = new ArrayDeque<>();
            // for every line, push all braces onto stack, but pop those who match the incoming brace ("<" matches incoming ">")
            // after that a stack only containing matchless braces remains
            for (String brace : line) {
                if (pairs.keySet().contains(brace)) {
                    stack.push(brace);
                } else {
                    if (pairs.get(stack.peek()).equals(brace)) {
                        stack.pop();
                    }
                }
            }
            counter = counter + getAndCountMissingBraces(stack);
            results.add(counter);
        }

        Collections.sort(results);
        System.out.println("The result of Day 10 Part Two is: " + results.get(results.size() / 2));
    }

    private static void partOne(List<List<String>> input) {
        int counter = 0;
        // iterate through every line
        for (int i = 0; i < input.size(); i++) {
            Deque<String> stack = new ArrayDeque<>();
            // iterate through every brace in every line
            for (int ii = 0; ii < input.get(i).size(); ii++) {
                String brace = input.get(i).get(ii);
                // if the brace is an opening brace, push onto stack
                if (pairs.keySet().contains(brace)) {
                    stack.push(brace);
                } else {
                    // if the brace is a closing brace, check on stack if it contains the matching opening brace, if so, pop stack
                    if (pairs.get(stack.peek()).equals(brace)) {
                        stack.pop();
                    // if not, the line is corrupted
                    } else {
                        counter = counter + countClosing(brace, true);
                        //System.out.println("Line " + (i + 1) + ": Expected: " + pairs.get(stack.peek()) + " but found: " + brace + ".");
                        break;
                    }
                }
            }
        }
        System.out.println("The result of Day 10 Part One is: " + counter);
    }
}



