package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class Vector2dTest {

    @Test
    void isEqualsCorrect(){
        //given
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(1,5);
        //when + then
        assertTrue(v1.equals(new Vector2d(1,2)));
        assertTrue(v1.equals(v1));
        assertFalse(v1.equals(v2));
        assertFalse(v1.equals(null));
    }

    @Test
    void isToStringCorrect(){
        Vector2d v = new Vector2d(1,1);
        assertEquals(v.toString(),"(1,1)");
    }

    @Test
    void isPrecedesCorrect(){
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(2,3);
        Vector2d v3 = new Vector2d(0,4);

        assertTrue(v1.precedes(v2));
        assertFalse(v1.precedes(v3));
        assertTrue(v1.precedes(v1));
    }

    @Test
    void isFollowsCorrect(){
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(2,3);
        Vector2d v3 = new Vector2d(0,4);

        assertTrue(v2.follows(v1));
        assertFalse(v3.follows(v1));
        assertTrue(v2.follows(v2));
    }

    @Test
    void isUpperRightCorrect(){
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(0,4);
        assertEquals(v1.upperRight(v2),new Vector2d(1,4));
    }

    @Test
    void isLowerLeftCorrect(){
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(0,4);
        assertEquals(v1.lowerLeft(v2),new Vector2d(0,1));
    }

    @Test
    void doesAddAddCorrect(){
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(0,4);
        assertEquals(v1.add(v2),new Vector2d(1,5));
    }

    @Test
    void doesSubtractSubCorrect(){
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(0,4);
        assertEquals(v1.subtract(v2),new Vector2d(1,-3));
    }

    @Test
    void isOppositeCorrect(){
        Vector2d v = new Vector2d(1,2);
        assertEquals(v.opposite(),new Vector2d(-1,-2));
    }
}