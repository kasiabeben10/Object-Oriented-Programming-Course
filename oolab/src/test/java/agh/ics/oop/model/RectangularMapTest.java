package agh.ics.oop.model;

import agh.ics.oop.model.util.IncorrectPositionException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    @Test
    void isRectangularMapInitializedCorrect(){
        RectangularMap map = new RectangularMap(4,4);
        assertEquals(map.getLowerLeft(), new Vector2d(0,0));
        assertEquals(map.getUpperRight(), new Vector2d(3,3));
        assertTrue(map.getAnimals().isEmpty());
    }

    @Test
    void doesGetAnimalsReturnsRectangularMapAnimals(){
        RectangularMap map = new RectangularMap(5,5);
        try {
            map.place(new Animal(new Vector2d(3,4)));
            map.place(new Animal(new Vector2d(4,2)));
        } catch (IncorrectPositionException e){
            fail("Exception: " + e.getMessage());
        }
        assertThrows(IncorrectPositionException.class, ()-> map.place(new Animal(new Vector2d(3,4))));
        Map<Vector2d, Animal> animals = map.getAnimals();
        assertEquals(animals.keySet().size(),2);
        assertTrue(animals.containsKey(new Vector2d(3,4)));
        assertTrue(animals.containsKey(new Vector2d(4,2)));
    }

    @Test
    void canNotMapPlaceAnimalOnAnotherAnimal(){
        RectangularMap map = new RectangularMap(10,10);
        Animal animal1 = new Animal(new Vector2d(2,3));
        Animal animal2 = new Animal(new Vector2d(2,3));
        Animal animal3 = new Animal(new Vector2d(3,9));

        try {
            map.place(animal1);
            map.place(animal3);
        } catch (IncorrectPositionException e) {
            fail("Exception: " + e.getMessage());
        }
        assertThrows(IncorrectPositionException.class, () -> map.place(animal2));
        assertEquals(map.getAnimals().size(),2);
        assertTrue(map.isOccupied(animal1.getPosition()));
        assertTrue(map.isOccupied(animal3.getPosition()));
    }

    @Test
    void canNotAnimalBePlacedOutOfRectangularField(){
        RectangularMap map = new RectangularMap(5,5);
        assertThrows(IncorrectPositionException.class, () -> map.place(new Animal(new Vector2d(13,10))));
        Map<Vector2d,Animal> animals = map.getAnimals();
        assertTrue(animals.isEmpty());
    }

    @Test
    void isCanMoveToCorrect(){
        RectangularMap map = new RectangularMap(10,10);
        Animal animal1 = new Animal(new Vector2d(2,2));
        Animal animal2 = new Animal(new Vector2d(2,1));

        try {
            map.place(animal1);
            map.place(animal2);
        } catch (IncorrectPositionException e) {
            fail("Exception: " + e.getMessage());
        }
        assertTrue(map.canMoveTo(new Vector2d(2, 3))); //can move to empty place next to animal
        assertFalse(map.canMoveTo(new Vector2d(2, 2))); //cannot move onto animal
        assertFalse(map.canMoveTo(new Vector2d(2,1))); //cannot move onto animal
        assertTrue(map.canMoveTo(new Vector2d(5,3))); //canMoveToEmptyPlace
        assertFalse(map.canMoveTo(new Vector2d(5,11)));//cannot move out of top boundary
        assertFalse(map.canMoveTo(new Vector2d(-1,2)));//cannot move out of left boundary
        assertFalse(map.canMoveTo(new Vector2d(13,2)));//cannot move out of right boundary
        assertFalse(map.canMoveTo(new Vector2d(7,-2)));//cannot move out of left boundary
    }
    @Test
    void areSingleMovesTrackedCorrect(){
        RectangularMap map = new RectangularMap(3,3);
        Animal animal= new Animal(new Vector2d(0,0));
        try {
            map.place(animal);
        } catch (IncorrectPositionException e) {
            fail("Exception: " + e.getMessage());
        }

        assertEquals(animal.getPosition(),new Vector2d(0,0));
        assertEquals(animal.getOrientation(), MapDirection.NORTH);

        map.move(animal, MoveDirection.RIGHT);
        assertEquals(animal.getPosition(),new Vector2d(0,0));
        assertEquals(animal.getOrientation(), MapDirection.EAST);

        map.move(animal, MoveDirection.FORWARD);
        assertEquals(animal.getPosition(),new Vector2d(1,0));
        assertEquals(animal.getOrientation(), MapDirection.EAST);

        map.move(animal, MoveDirection.RIGHT);
        assertEquals(animal.getPosition(),new Vector2d(1,0));
        assertEquals(animal.getOrientation(), MapDirection.SOUTH);

        map.move(animal, MoveDirection.BACKWARD);
        assertEquals(animal.getPosition(),new Vector2d(1,1));
        assertEquals(animal.getOrientation(), MapDirection.SOUTH);

        map.move(animal, MoveDirection.LEFT);
        assertEquals(animal.getPosition(),new Vector2d(1,1));
        assertEquals(animal.getOrientation(), MapDirection.EAST);
    }

    @Test
    void isNotAnimalMovedOutOfBoundaries(){
        Animal animal = new Animal(new Vector2d(0,0));
        RectangularMap map = new RectangularMap(5,5);
        try {
            map.place(animal);
        } catch (IncorrectPositionException e) {
            fail("Exception: " + e.getMessage());
        }

        //bottom
        map.move(animal, MoveDirection.BACKWARD);
        assertEquals(animal.getPosition(),new Vector2d(0,0));
        assertTrue(animal.getPosition().follows(map.getLowerLeft()));
        assertTrue(animal.getPosition().precedes(map.getUpperRight()));


        //left
        map.move(animal, MoveDirection.LEFT);
        map.move(animal, MoveDirection.FORWARD);
        assertEquals(animal.getPosition(),new Vector2d(0,0));
        assertTrue(animal.getPosition().follows(map.getLowerLeft()));
        assertTrue(animal.getPosition().precedes(map.getUpperRight()));

        //top
        map.move(animal, MoveDirection.RIGHT);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        assertEquals(animal.getPosition(),new Vector2d(0,4));
        assertTrue(animal.getPosition().follows(map.getLowerLeft()));
        assertTrue(animal.getPosition().precedes(map.getUpperRight()));

        //right
        map.move(animal, MoveDirection.RIGHT);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        assertEquals(animal.getPosition(),new Vector2d(4,4));
        assertTrue(animal.getPosition().follows(map.getLowerLeft()));
        assertTrue(animal.getPosition().precedes(map.getUpperRight()));
    }

    @Test
    void isNotAnimalMovedOnAnotherAnimal(){
        RectangularMap map = new RectangularMap(10,10);
        Animal animal1 = new Animal(new Vector2d(5,5));
        Animal animal2 = new Animal(new Vector2d(6,5));
        try {
            map.place(animal1);
            map.place(animal2);
        } catch (IncorrectPositionException e) {
            fail("Exception: " + e.getMessage());
        }

        map.move(animal1,MoveDirection.RIGHT);
        map.move(animal1,MoveDirection.FORWARD);

        assertEquals(animal1.getPosition(),new Vector2d(5,5));
        assertEquals(animal2.getPosition(),new Vector2d(6,5));
    }

    @Test
    void areAnimalPositionsOccupiedAndEmptyPlaceNotOccupied(){
        RectangularMap map = new RectangularMap(10,10);

        try {
            map.place(new Animal(new Vector2d(3,4)));
            map.place(new Animal(new Vector2d(1,8)));
            map.place(new Animal(new Vector2d(3,5)));
            map.place(new Animal(new Vector2d(0,0)));
        } catch (IncorrectPositionException e) {
            fail("Exception: " + e.getMessage());
        }
        Map<Vector2d, Animal> animals = map.getAnimals();

        for (int x=0; x<10; x++){
            for (int y=0; y<10; y++){
                if (animals.containsKey(new Vector2d(x,y))){
                    assertTrue(map.isOccupied(new Vector2d(x,y)));
                }
                else {
                    assertFalse(map.isOccupied(new Vector2d(x,y)));
                }
            }
        }
    }


    @Test
    void doesObjectAtReturnsAnimalFromAnimalsPositionsAndNullFromEmptyPosition(){
        RectangularMap map = new RectangularMap(4,4);
        Animal animal1 = new Animal(new Vector2d(3,3));
        Animal animal2 = new Animal(new Vector2d(1,2));
        try {
            map.place(animal1);
            map.place(animal2);
        } catch (IncorrectPositionException e) {
            fail("Exception: " + e.getMessage());
        }
        map.move(animal1, MoveDirection.RIGHT);
        Map<Vector2d,Animal> animals = map.getAnimals();

        for (int x=0; x<10; x++){
            for (int y=0; y<10; y++){
                if (x==3 && y==3){
                    assertNotNull(map.objectAt(new Vector2d(x,y)));
                    assertEquals(animals.get(new Vector2d(x,y)).getOrientation(),MapDirection.EAST);
                }
                else if (x==1 && y==2){
                    assertNotNull(map.objectAt(new Vector2d(x,y)));
                    assertEquals(animals.get(new Vector2d(x,y)).getOrientation(),MapDirection.NORTH);
                }
                else {
                    assertNull(map.objectAt(new Vector2d(x,y)));
                }
            }
        }
    }

    @Test
    void isRectangularMapStringDrawnCorrect(){
        RectangularMap map = new RectangularMap(3,3);
        try {
            map.place(new Animal(new Vector2d(1,1)));
            map.place(new Animal(new Vector2d(0,2)));
        } catch (IncorrectPositionException e) {
            fail("Exception: " + e.getMessage());
        }

        assertEquals("""
                 y\\x  0 1 2\r
                  3: -------\r
                  2: |N| | |\r
                  1: | |N| |\r
                  0: | | | |\r
                 -1: -------\r
                """, map.toString());
    }

    @Test
    void doesGetElementsReturnsMapAnimals(){
        RectangularMap map = new RectangularMap(3,3);
        Animal animal1 = new Animal(new Vector2d(1,1));
        Animal animal2 = new Animal(new Vector2d(0,2));
        Animal animal3 = new Animal(new Vector2d(1,1));
        try {
            map.place(animal1);
            map.place(animal2);
        } catch (IncorrectPositionException e) {
            fail("Exception: " + e.getMessage());
        }
        List<WorldElement> animals = map.getElements();

        assertEquals(animals.size(),2);
        assertTrue(animals.contains(animal1));
        assertTrue(animals.contains(animal2));
        assertFalse(animals.contains(animal3));
    }
}