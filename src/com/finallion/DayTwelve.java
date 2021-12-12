package com.finallion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DayTwelve {

    public static void dayTwelve() throws IOException {
        String path = Main.getPath("Twelve");

        Set<Cave> caveSystem = new HashSet<>();

        List<String> lines = Files.lines(Paths.get(path)).collect(Collectors.toList());
        for (String line : lines) {
            String[] parts = line.split("-");
            caveSystem.add(new Cave(parts[0]));
            caveSystem.add(new Cave(parts[1]));
        }

        for (Cave cave : caveSystem) {
            for (String line : lines) {
                String[] parts = line.split("-");

                if (parts[1].equals(cave.getCaveName())) {
                    cave.addParentCaves(new Cave(parts[0]));
                    cave.addChildCaves(new Cave(parts[0]));
                }

                if (parts[0].equals(cave.getCaveName())) {
                    cave.addParentCaves(new Cave(parts[1]));
                    cave.addChildCaves(new Cave(parts[1]));
                }
            }
        }

        //printCaveSystem(caveSystem);
        traverseCaveSystem(caveSystem);
    }

    // because I added "new Cave" to parents and children while reading from file, the child of cave doesn't have any children. But there is a cave that matches the child cave.
    private static Cave getMatchingCave(Set<Cave> caveSystem, Cave cave) {
        for (Cave c : caveSystem) {
            if (c.equals(cave)) {
                return c;
            }
        }
        return cave;
    }

    private static void traverseCaveSystem(Set<Cave> caveSystem) {
        List<List<Cave>> allPaths = new ArrayList<>();

        for (Cave cave : caveSystem) {
            if (cave.isStart()) {
                // iterate through all start caves
                for (Cave child : cave.getChildCaves()) {
                    // create for every start cave a new path
                    List<Cave> path = new ArrayList<>();
                    path.add(cave);
                    path.add(child);

                    // add these initial paths to the final result list
                    allPaths.add(path);
                    // start recursive call of children caves
                    allPaths.addAll(getChildCaves(child, path, caveSystem));
                }
            }
        }

        allPaths.removeIf(caves -> !caves.contains(new Cave("end")));
        // check out line 141 for Part One
        //System.out.println("The result of Day Twelve Part One is: " + allPaths.size());
        System.out.println("The result of Day Twelve Part Two is: " + allPaths.size());
    }

    private static boolean containsTwo(List<Cave> path, Cave toBeAdded) {
        List<Cave> smallCaves = new ArrayList<>();

        // add cave hypothetically and check if the rules apply
        smallCaves.add(toBeAdded);

        // filter small caves
        for (int i = 0; i < path.size(); i++) {
            Cave cave = path.get(i);
            if (cave.isSmallCave(cave)) {
                smallCaves.add(cave);
            }
        }

        // get all duplicates
        Set<Cave> duplicates = new LinkedHashSet<>();
        Set<Cave> uniques = new HashSet<>();

        for (Cave cave : smallCaves) {
            if (!uniques.add(cave)) {
                duplicates.add(cave);
            }
        }

        // iterate through all duplicates and count their appearance in the path
        boolean passOne = true;
        for (Cave dup : duplicates) {
            int counter = 0;
            for (Cave small : smallCaves) {
                if (small.equals(dup)) {
                    counter++;
                }
            }

            if (!passOne) {
                return false;
            }

            // needed to pass the "one small cave can be used two times"-rule
            if (counter == 2) {
                passOne = false;
            }

            // same rule, but out of bounds
            if (counter > 2) {
                return false;
            }
        }
        return true;
    }

    private static List<List<Cave>> getChildCaves(Cave cave, List<Cave> path, Set<Cave> caveSystem) {
        // get actual cave in the system
        cave = getMatchingCave(caveSystem, cave);
        List<List<Cave>> allPaths = new ArrayList<>();

        for (Cave children : cave.getChildCaves()) {
            // new path for each child, containing the path of its parent
            List<Cave> childPath = new ArrayList<>(path);


            // if child is small cave and path already contains child, skip
            // change !containsTwo to childPath.contains(children) to get result for part one
            if (children.isSmallCave(children) && !containsTwo(childPath, children)) {
                continue;
            }

            // add child to path
            childPath.add(children);

            allPaths.add(childPath);

            // if child is not the end cave restart recursive call
            if (!children.isEnd()) {
                allPaths.addAll(getChildCaves(children, childPath, caveSystem));
            }
        }

        return allPaths;
    }


    private static void printCaveSystem(Set<Cave> caveSystem) {
        for (Cave cave : caveSystem) {
            System.out.println("Cave: " + cave.getCaveName() + ", Parent: " + cave.getParentCaves() + ", Child: " + cave.getChildCaves());
        }
    }


    public static class Cave {
        private List<Cave> parentCaves  = new ArrayList<>();;
        private List<Cave> childCaves = new ArrayList<>();
        String caveName;

        public Cave(String caveName) {
            this.caveName = caveName;
        }

        public List<Cave> getParentCaves() {
            return parentCaves;
        }

        public List<Cave> getChildCaves() {
            return childCaves;
        }

        public String getCaveName() {
            return caveName;
        }

        public void addChildCaves(Cave cave) {
            // end can't have children
            if (this.getCaveName().equals("end")) {
                return;
            }

            // start cave can't be child
            if (cave.getCaveName().equals("start")) {
                return;
            }

            this.childCaves.add(cave);
        }

        public void addParentCaves(Cave cave) {
            // start cave can't have parents
            if (this.getCaveName().equals("start")) {
                return;
            }

            // end cave can't be parent
            if (cave.getCaveName().equals("end")) {
                return;
            }

            this.parentCaves.add(cave);
        }

        public boolean isStart() {
            for (Cave parent : this.getParentCaves()) {
                if (parent.getCaveName().equals("start")) {
                    return true;
                }
            }
            return false;
        }

        public boolean isEnd() {
            return this.getCaveName().equals("end");
        }

        public boolean isSmallCave(Cave cave) {
            if (cave.getCaveName().equals("start") || cave.getCaveName().equals("end")) {
                return false;
            }

            return Character.isLowerCase(cave.getCaveName().charAt(0));
        }


        @Override
        public String toString() {
            return caveName;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            final Cave other = (Cave) obj;

            if (!this.caveName.equals(other.caveName)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Objects.hash(caveName);
        }
    }



}
