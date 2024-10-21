package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import java.util.Arrays;

public class OptionsParser {
    public static MoveDirection[] parse(String[] args) {
        MoveDirection[] directionsArray = new MoveDirection[args.length];
        int currentIndex = 0;
        boolean flag;
        for (String arg : args) {
            flag = true;
            switch (arg) {
                case "f" -> directionsArray[currentIndex] = MoveDirection.FORWARD;
                case "b" -> directionsArray[currentIndex] = MoveDirection.BACKWARD;
                case "l" -> directionsArray[currentIndex] = MoveDirection.LEFT;
                case "r" -> directionsArray[currentIndex] = MoveDirection.RIGHT;
                default -> flag = false;
            };
            if (flag) {
                currentIndex++;
            }
        }
        return Arrays.copyOfRange(directionsArray,0,currentIndex);
    }
}
