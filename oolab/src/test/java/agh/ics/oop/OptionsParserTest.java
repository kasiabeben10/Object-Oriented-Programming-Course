package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class OptionsParserTest {

    @Test
    void isfParsedToForward(){
        String[] given = {"f"};
        List<MoveDirection> expected = List.of(MoveDirection.FORWARD);
        assertEquals(expected, OptionsParser.parse(given));
    }

    @Test
    void isbParsedToBackward(){
        String[] given = {"b"};
        List<MoveDirection> expected = List.of(MoveDirection.BACKWARD);
        assertEquals(expected, OptionsParser.parse(given));
    }

    @Test
    void isrParsedToRight(){
        String[] given = {"r"};
        List<MoveDirection> expected = List.of(MoveDirection.RIGHT);
        assertEquals(expected, OptionsParser.parse(given));
    }

    @Test
    void islParsedToLeft(){
        String[] given = {"l"};
        List<MoveDirection> expected = List.of(MoveDirection.LEFT);
        assertEquals(expected, OptionsParser.parse(given));
    }

    @Test
    void doesUnknownArgMakeParserThrowsException(){
        String[] given = {"k"};
        assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(given));
    }

    @Test
    void doesLongerArrayWithUnknownArgMakeParserThrowsException(){
        String[] given = {"l", "f", "q", "+", "b", "b", "e", "r"};
        assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(given));
    }

    @Test
    void isLongerArrayParsedCorrect(){
        String[] given = {"l", "f", "b", "b", "r"};
        List<MoveDirection> expected = List.of(MoveDirection.LEFT, MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT);
        assertEquals(expected, OptionsParser.parse(given));
    }


}