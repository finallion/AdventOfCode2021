package com.finallion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DayFifteen {

    public static void dayFifteen() throws IOException {
        String path = Main.getPath("Fifteen");

        List<String> lines = Files.lines(Paths.get(path)).toList();
        List<Node> nodes = new ArrayList<>();
        int[][] values = new int[lines.size()][lines.get(0).length()];


        for (int i = 0; i < lines.size(); i++) {
            String[] numbers = lines.get(i).split("");
            for (int ii = 0; ii < lines.get(i).length(); ii++) {
                // init every node with risk level MAX
                nodes.add(new Node(i, ii, Integer.MAX_VALUE));
                // but keep all risk levels saved for later use
                values[i][ii] = Integer.parseInt(numbers[ii]);
            }
        }

        partOne(nodes, values);
        partTwo(nodes, values);
    }

    private static void partTwo(List<Node> nodes, int[][] values) {
        // our matrix is now part of a 5x5 matrix (upper left tile)
        // each tile has a one higher value than its left or diagonal left neighbor
        int[][] caveValues = new int[5 * values.length][5 * values[0].length];


        // fill the first 5 tile in the first row
        for (int i = 0; i < values.length; i++) {
            int addOneToCols = 0;
            for (int ii = 0; ii < caveValues[0].length; ii++) {
                // for every matrix-length raise the
                if (ii % values[0].length == 0 && ii != 0) {
                    addOneToCols++;
                }

                // 10 turns to 1, 11 to 2, 12 to 3 etc.
                int value = values[i % values.length][ii % values[0].length] + addOneToCols;
                if (value >= 10) caveValues[i][ii] = (value % 10) + 1;
                else caveValues[i][ii] = value;

                // create and add new node
                nodes.add(new Node(i, ii, Integer.MAX_VALUE));
            }
        }

        // fill the next 4 rows by coping the first row downwards and increasing every value by 1 for each row
        int addOneToRows = 1;
        for (int i = values.length; i < caveValues.length; i++) {

            if (i % values.length == 0 && i != values.length) {
                addOneToRows++;
            }

            for (int ii = 0; ii < caveValues[0].length; ii++) {
                // for every value, get the value from the first already completed matrix-row and add the addOneToRows
                int value = caveValues[i % values.length][ii] + addOneToRows;
                if (value >= 10) caveValues[i][ii] = (value % 10) + 1;
                else caveValues[i][ii] = value;

                // create and add new node
                nodes.add(new Node(i, ii, Integer.MAX_VALUE));
            }

        }

        // takes about 3 min to calculate
        System.out.print("The result of Day Fifteen Part Two is: ");
        System.out.println(dijkstrasAlgorith(nodes, caveValues));
    }

    private static int dijkstrasAlgorith(List<Node> nodes, int[][] values) {
        // sorts the queue by comparing the third value (risk)
        // we have the coordinate as node and the risk of this coordinate
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparing(Node::getRisk));

        // start point at 0,0 and set risk level to 0
        Node source = nodes.get(0);
        source.setRisk(0);
        queue.add(source);

        while (!queue.isEmpty()) {
            // get the lowest risk node from queue
            Node currentNode = queue.poll();

            if (currentNode.getRow() == values.length && currentNode.getCol() == values[0].length) {
                break;
            }

            // get all neighbors from polled node
            for (Node node : getAdjacentNodes(currentNode, nodes)) {
                // get the risk level of the current node and add the risk level (from our helper array) from the neighbor
                int currentRisk = currentNode.getRisk() + values[node.getRow()][node.getCol()];

                // check if the neighbor node was already visited
                // the currentRisk is the risk of our current node and the next node
                // node.getRisk() should be MAX, or else the node was already visited
                if (currentRisk < node.getRisk()) {
                    if (node.getRisk() != Integer.MAX_VALUE) {
                        queue.remove(node);
                    }
                    node.setRisk(currentRisk);
                    queue.add(node);
                }
            }
        }

        return nodes.get(nodes.size() - 1).getRisk();
    }

    private static void partOne(List<Node> nodes, int[][] values) {
        System.out.print("The result of Day Fifteen Part One is: ");
        System.out.println(dijkstrasAlgorith(nodes, values));
    }

    private static List<Node> getAdjacentNodes(Node current, List<Node> nodes) {
        List<Node> adjacentNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.getRow() == current.getRow()) {
                if (node.getCol() == current.getCol() + 1) adjacentNodes.add(node);
                if (node.getCol() == current.getCol() - 1) adjacentNodes.add(node);
            }

            if (node.getCol() == current.getCol()) {
                if (node.getRow() == current.getRow() + 1) adjacentNodes.add(node);
                if (node.getRow() == current.getRow() - 1) adjacentNodes.add(node);
            }
        }

        return adjacentNodes;
    }

    private static class Node {
        int row;
        int col;
        int risk;

        public Node(int x, int y, int risk) {
            this.row = x;
            this.col = y;
            this.risk = risk;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public void setRisk(int risk) {
            this.risk = risk;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return row == node.row && col == node.col;
        }

        @Override
        public String toString() {
            return "[" + row + "-" + col + "]: " + risk;
        }

        public int getRisk() {
            return risk;
        }


    }
}


