package com.finallion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public final static String path = "C:\\Users\\lbret\\Desktop\\Modding\\AdventOfCode2021\\src\\com\\finallion\\resources\\Day";


    public static void main(String[] args) throws IOException {
        //DayOne.dayOne();
        //DayTwo.dayTwo();
        //DayThree.dayThree();
        //DayFour.dayFour();
        //DayFive.dayFive();
        //DaySix.daySix();
        //DaySeven.daySeven(); DaySeven.daySevenBetter();
        //DayEight.dayEight();
        DayNine.dayNine();
    }

    public static String getPath(String dayNumber) {
        return path + dayNumber + "Input";

    }

    public static class Pair {
        int xNode;
        int yNode;

        public Pair(int x, int y) {
            this.xNode = x;
            this.yNode = y;
        }

        public int getX() {
            return xNode;
        }

        public int getY() {
            return yNode;
        }

        @Override
        public String toString() {
            return "X:" + getX() + ", Y:" + getY();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            final Pair other = (Pair) obj;
            if (this.xNode != other.xNode) {
                return false;
            }

            if (this.yNode != other.yNode) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return Objects.hash(xNode, yNode);
        }
    }






}
