package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class OptionsParserTest {

    @Test
    void isfParsedToForward(){
        String[] given = {"f"};
        MoveDirection[] expected = {MoveDirection.FORWARD};
        assertArrayEquals(expected, OptionsParser.parse(given));
    }

    @Test
    void isbParsedToBackward(){
        String[] given = {"b"};
        MoveDirection[] expected = {MoveDirection.BACKWARD};
        assertArrayEquals(expected, OptionsParser.parse(given));
    }

    @Test
    void isrParsedToRight(){
        String[] given = {"r"};
        MoveDirection[] expected = {MoveDirection.RIGHT};
        assertArrayEquals(expected, OptionsParser.parse(given));
    }

    @Test
    void islParsedToLeft(){
        String[] given = {"l"};
        MoveDirection[] expected = {MoveDirection.LEFT};
        assertArrayEquals(expected, OptionsParser.parse(given));
    }

    @Test
    void isUnknownParsedToNothing(){
        String[] given = {"k"};
        MoveDirection[] expected = {};
        assertArrayEquals(expected, OptionsParser.parse(given));
    }

    @Test
    void isLongerArrayParsedCorrect(){
        String[] given = {"l", "f", "q", "+", "b", "b", "e", "r"};
        MoveDirection[] expected = {MoveDirection.LEFT, MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT};
        assertArrayEquals(expected, OptionsParser.parse(given));
    }


}