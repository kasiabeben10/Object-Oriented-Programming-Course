package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class OptionsParser {
    public static List<MoveDirection> parse(String[] args) {
        List<MoveDirection> directionsArray = new ArrayList<>();
        for (String arg : args) {
            switch (arg) {
                case "f" -> directionsArray.add(MoveDirection.FORWARD);
                case "b" -> directionsArray.add(MoveDirection.BACKWARD);
                case "l" -> directionsArray.add(MoveDirection.LEFT);
                case "r" -> directionsArray.add(MoveDirection.RIGHT);
                default -> throw new IllegalArgumentException(arg + " is not legal move specification");
            };

        }
        return directionsArray;
    }
}
