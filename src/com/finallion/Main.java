package com.finallion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public final static String path = "C:\\Users\\lbret\\Desktop\\Modding\\AdventOfCode2021\\src\\com\\finallion\\ressources\\Day";


    public static void main(String[] args) throws IOException {
        //DayOne.dayOne();
        //DayTwo.dayTwo();
        //DayThree.dayThree();
        //DayFour.dayFour();
        //DayFive.dayFive();
        DaySix.daySix();
    }

    public static String getPath(String dayNumber) {
        return path + dayNumber + "Input";

    }






}
