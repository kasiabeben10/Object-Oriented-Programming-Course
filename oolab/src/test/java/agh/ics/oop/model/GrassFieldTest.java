package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    @Test
    void isGrassFieldInitializedCorrect(){
        GrassField map = new GrassField(5);

        assertEquals(map.getGrasses().size(),5);
        assertTrue(map.getAnimals().isEmpty());
    }

    @Test
    void doesGetAnimalsReturnsGrassFieldAnimals(){
        GrassField map = new GrassField(4);
        Animal animal1 = new Animal(new Vector2d(3,4));
        Animal animal2 = new Animal(new Vector2d(4,2));
        Animal animal3 = new Animal(new Vector2d(3,4));

        map.place(animal1);
        map.move(animal1, MoveDirection.LEFT);
        map.place(animal2);
        map.move(animal2,MoveDirection.RIGHT);
        map.place(animal3);
        Map<Vector2d, Animal> animals = map.getAnimals();

        assertEquals(animals.keySet().size(),2);
        assertTrue(animals.containsKey(new Vector2d(3,4)));
        assertTrue(animals.containsKey(new Vector2d(4,2)));
        assertEquals(animals.get(new Vector2d(3,4)).getOrientation(),MapDirection.WEST);
        assertEquals(animals.get(new Vector2d(4,2)).getOrientation(),MapDirection.EAST);
    }

    @Test
    void doesGetGrassesReturnsGrassFieldGrasses(){
        GrassField map = new GrassField(4);
        map.place(new Animal(new Vector2d(3,4)));
        Map<Vector2d, Grass> grasses = map.getGrasses();
        assertEquals(grasses.keySet().size(),4);
        for (Vector2d position : grasses.keySet()){
            assertTrue(position.follows(new Vector2d(0,0)));
            assertTrue(position.precedes(new Vector2d((int)sqrt(10*4),(int)sqrt(10*4))));
        }
    }

    @Test
    void canNotMapPlaceAnimalOnAnotherAnimal(){
        GrassField map = new GrassField(10);
        Animal animal1 = new Animal(new Vector2d(2,3));
        Animal animal2 = new Animal(new Vector2d(2,3));
        Animal animal3 = new Animal(new Vector2d(3,9));

        assertTrue(map.place(animal1) && map.place(animal3));
        assertFalse(map.place(animal2));
        assertEquals(map.getGrasses().size(),10);
        assertEquals(map.getAnimals().size(),2);
        assertTrue(map.isOccupied(animal1.getPosition()));
        assertTrue(map.isOccupied(animal3.getPosition()));
        assertTrue(map.objectAt(animal1.getPosition()) instanceof Animal);
        assertTrue(map.objectAt(animal3.getPosition()) instanceof Animal);
    }

    @Test
    void canMapPlaceAnimalOnGrass(){
        GrassField map = new GrassField(5);
        Map<Vector2d,Grass> grasses = map.getGrasses();
        for (Vector2d position : grasses.keySet()){
            assertTrue(map.place(new Animal(position)));
        }
    }

    @Test
    void canAnimalBePlacedOutOfGrassRectangularField(){
        GrassField map = new GrassField(5);
        map.place(new Animal(new Vector2d(50,50)));
    }


    @Test
    void isCanMoveToCorrect(){
        GrassField map = new GrassField(10); //(0,0) - (10,10)
        Animal animal1 = new Animal(new Vector2d(2,2));
        Animal animal2 = new Animal(new Vector2d(2,1));

        assertTrue(map.place(animal1) && map.place(animal2));
        assertTrue(map.canMoveTo(new Vector2d(2, 3)));
        assertFalse(map.canMoveTo(new Vector2d(2, 2)));
        assertFalse(map.canMoveTo(new Vector2d(2,1)));

        for (int x=0; x<=10; x++){
            for (int y=0; y<=10; y++){
                if (!(map.objectAt(new Vector2d(x,y)) instanceof Animal)){
                    assertTrue(map.canMoveTo(new Vector2d(x,y))); //can move onto grass or empty place
                }
                else{
                    assertFalse(map.canMoveTo(new Vector2d(x,y))); //cannot move on another animal
                }
            }
        }
        assertTrue(map.canMoveTo(new Vector2d(30,22))); //canMoveToEmptyPlaceOutOfGrassArea
    }

    @Test
    void areSingleMovesTrackedCorrect(){
        GrassField map = new GrassField(3);
        Animal animal= new Animal(new Vector2d(0,0));
        map.place(animal);

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
    void canAnimalGoOutOfGrassRectangularField(){
        GrassField map = new GrassField(3);//(0,0)-(5,5)
        Animal animal= new Animal(new Vector2d(0,0));
        map.place(animal);
        map.move(animal,MoveDirection.LEFT);
        map.move(animal,MoveDirection.FORWARD);
        map.move(animal,MoveDirection.FORWARD);
        assertEquals(animal.getPosition(),new Vector2d(-2,0)); //LEFT
        map.move(animal,MoveDirection.BACKWARD);
        map.move(animal,MoveDirection.BACKWARD);
        map.move(animal, MoveDirection.RIGHT);
        map.move(animal, MoveDirection.BACKWARD);
        assertEquals(animal.getPosition(), new Vector2d(0,-1)); //BOTTOM
        map.move(animal, MoveDirection.RIGHT);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        assertEquals(animal.getPosition(), new Vector2d(6,-1)); //RIGHT
        map.move(animal, MoveDirection.LEFT);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        map.move(animal, MoveDirection.FORWARD);
        assertEquals(animal.getPosition(), new Vector2d(6,7)); //TOP
    }

    @Test
    void isNotAnimalMovedOnAnotherAnimal(){
        GrassField map = new GrassField(5);
        Animal animal1 = new Animal(new Vector2d(5,5));
        Animal animal2 = new Animal(new Vector2d(5,6));
        Animal animal3 = new Animal(new Vector2d(4,5));
        map.place(animal1);
        map.place(animal2);
        map.move(animal1,MoveDirection.FORWARD);
        assertEquals(animal1.getPosition(),new Vector2d(5,5));
        assertEquals(animal2.getPosition(),new Vector2d(5,6));
        map.move(animal2,MoveDirection.BACKWARD);
        assertEquals(animal1.getPosition(),new Vector2d(5,5));
        assertEquals(animal2.getPosition(),new Vector2d(5,6));
        map.move(animal3,MoveDirection.RIGHT);
        map.move(animal3,MoveDirection.FORWARD);
        assertEquals(animal3.getPosition(),new Vector2d(4,5));
        assertEquals(animal2.getPosition(),new Vector2d(5,6));
        map.move(animal1,MoveDirection.LEFT);
        map.move(animal1,MoveDirection.FORWARD);
        assertEquals(animal3.getPosition(),new Vector2d(4,5));
        assertEquals(animal1.getPosition(),new Vector2d(5,5));
    }

    @Test
    void isAnimalMovedOnGrass(){
        GrassField map = new GrassField(5);
        Map<Vector2d,Grass> grasses = map.getGrasses();
        for (Vector2d position : grasses.keySet()){
            Animal animal = new Animal(new Vector2d(position.getX(), position.getY()-1));
            map.place(animal);
            map.move(animal,MoveDirection.FORWARD);
            assertEquals(animal.getPosition(),position);
        }

    }

    @Test
    void areAnimalAndGrassesPositionsOccupiedAndEmptyPlaceNotOccupied(){
        GrassField map = new GrassField(10);
        Map<Vector2d,Grass> grasses = map.getGrasses();
        Animal animal = new Animal();
        map.place(animal);
        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        for (Vector2d position : grasses.keySet()){
            assertTrue(map.isOccupied(position));
        }
        assertFalse(map.isOccupied(new Vector2d(31,55)));
    }

    @Test
    void doesObjectAtReturnAppropriateObject(){
        GrassField map = new GrassField(3);
        Map<Vector2d, Grass> grasses = map.getGrasses();
        for (Vector2d position : grasses.keySet()){
            assertTrue(map.objectAt(position) instanceof Grass);
            assertFalse(map.objectAt(position) instanceof Animal);
            map.place(new Animal(position));
            assertFalse(map.objectAt(position) instanceof Grass);
            assertTrue(map.objectAt(position) instanceof Animal);
        }
    }

    @Test
    void isNullReturnedByObjectAtOnEmptyPosition(){
        GrassField map = new GrassField(5);
        assertNull(map.objectAt(new Vector2d(10,10)));
        //pole będzie wolne bo kępki są między (0,0) a (7,7)
    }

    @Test
    void doesGetElementsReturnsMapAnimalsAndGrasses(){
        GrassField map = new GrassField(3);
        Animal animal1 = new Animal(new Vector2d(1,1));
        Animal animal2 = new Animal(new Vector2d(0,2));
        Animal animal3 = new Animal(new Vector2d(1,1));
        map.place(animal1);
        map.place(animal2);
        List<WorldElement> mapElements = map.getElements();

        assertEquals(mapElements.size(),2+3);
        assertTrue(mapElements.contains(animal1));
        assertTrue(mapElements.contains(animal2));
        assertFalse(mapElements.contains(animal3));
    }

}