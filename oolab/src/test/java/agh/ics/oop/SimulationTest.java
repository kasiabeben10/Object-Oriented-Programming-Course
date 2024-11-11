package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void areAnimalsWithNotValidPositionNotPlacedAndDoesOneAnimalsMovesCorrect(){
        List<MoveDirection> directions = new ArrayList<>();
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.LEFT);
        directions.add(MoveDirection.FORWARD);
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal(new Vector2d (3,3)));
        animals.add(new Animal(new Vector2d (3,3)));
        animals.add(new Animal(new Vector2d (3,5)));
        RectangularMap map = new RectangularMap(5,5);
        Simulation<Animal,Vector2d> simulation = new Simulation<Animal,Vector2d>(animals, directions, map);
        List<Animal> mapAnimals = simulation.getMapObjects();
        simulation.run();

        assertEquals(mapAnimals.size(),1);
        Animal animal = mapAnimals.get(0);
        assertSame(animal.getOrientation(), MapDirection.WEST);
        assertTrue(animal.isAt(new Vector2d(2,4)));
        assertTrue(animal.getPosition().follows(map.getLowerLeft()));
        assertTrue(animal.getPosition().precedes(map.getUpperRight()));
    }

    @Test
    void areOrientationAndPositionCorrect(){
        List <MoveDirection> directions = new ArrayList<>();
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.LEFT);
        directions.add(MoveDirection.BACKWARD);
        directions.add(MoveDirection.RIGHT);
        directions.add(MoveDirection.BACKWARD);
        directions.add(MoveDirection.BACKWARD);
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal(new Vector2d (2,2)));
        animals.add(new Animal(new Vector2d (4,3)));
        animals.add(new Animal(new Vector2d (0,1)));
        RectangularMap map = new RectangularMap(8,8);
        Simulation<Animal,Vector2d> simulation = new Simulation<Animal, Vector2d>(animals, directions, map);
        List<Animal> mapAnimals = simulation.getMapObjects();
        simulation.run();
        assertSame(mapAnimals.get(0).getOrientation(), MapDirection.EAST);
        assertTrue(mapAnimals.get(0).isAt(new Vector2d(2,3)));
        assertSame(mapAnimals.get(1).getOrientation(), MapDirection.WEST);
        assertTrue(mapAnimals.get(1).isAt(new Vector2d(5,3)));
        assertSame(mapAnimals.get(2).getOrientation(), MapDirection.NORTH);
        assertTrue(mapAnimals.get(2).isAt(new Vector2d(0,0)));
    }


    @Test
    void canNotAnimalBeOutOfBoundaries(){
        Animal animal = new Animal(new Vector2d(0,0));
        RectangularMap map = new RectangularMap(5,5);

        //bottom
        animal.move(MoveDirection.BACKWARD, map);
        assertEquals(animal.getPosition(),new Vector2d(0,0));
        assertTrue(animal.getPosition().follows(map.getLowerLeft()));
        assertTrue(animal.getPosition().precedes(map.getUpperRight()));


        //left
        animal.move(MoveDirection.LEFT, map);
        animal.move(MoveDirection.FORWARD, map);
        assertEquals(animal.getPosition(),new Vector2d(0,0));
        assertTrue(animal.getPosition().follows(map.getLowerLeft()));
        assertTrue(animal.getPosition().precedes(map.getUpperRight()));

        //top
        animal.move(MoveDirection.RIGHT,map);
        animal.move(MoveDirection.FORWARD,map);
        animal.move(MoveDirection.FORWARD,map);
        animal.move(MoveDirection.FORWARD,map);
        animal.move(MoveDirection.FORWARD,map);
        animal.move(MoveDirection.FORWARD,map);
        animal.move(MoveDirection.FORWARD,map);
        animal.move(MoveDirection.FORWARD,map);
        assertEquals(animal.getPosition(),new Vector2d(0,4));
        assertTrue(animal.getPosition().follows(map.getLowerLeft()));
        assertTrue(animal.getPosition().precedes(map.getUpperRight()));

        //right
        animal.move(MoveDirection.RIGHT,map);
        animal.move(MoveDirection.FORWARD,map);
        animal.move(MoveDirection.FORWARD,map);
        animal.move(MoveDirection.FORWARD,map);
        animal.move(MoveDirection.FORWARD,map);
        animal.move(MoveDirection.FORWARD,map);
        animal.move(MoveDirection.FORWARD,map);
        animal.move(MoveDirection.FORWARD,map);
        assertEquals(animal.getPosition(),new Vector2d(4,4));
        assertTrue(animal.getPosition().follows(map.getLowerLeft()));
        assertTrue(animal.getPosition().precedes(map.getUpperRight()));
    }

    @Test
    void areStringArgsInterpretedCorrect(){
        String[] args = {"f","l","x","r","b","e","l","b"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Animal> animals= new ArrayList<>();
        animals.add(new Animal(new Vector2d(2,2)));
        animals.add(new Animal(new Vector2d(3,4)));
        Simulation<Animal,Vector2d> simulation = new Simulation<Animal, Vector2d>(animals, directions,new RectangularMap(5,5));
        List<Animal> mapAnimals = simulation.getMapObjects();

        for (int i=0;i<mapAnimals.size();i++){
            assertSame(mapAnimals.get(i).getOrientation(), MapDirection.NORTH);
            assertEquals(mapAnimals.get(i).getPosition(),animals.get(i).getPosition());
        }
        assertEquals(MoveDirection.FORWARD,directions.get(0));
        assertEquals(MoveDirection.LEFT,directions.get(1));
        assertEquals(MoveDirection.RIGHT,directions.get(2));
        assertEquals(MoveDirection.BACKWARD,directions.get(3));
        assertEquals(MoveDirection.LEFT,directions.get(4));
        assertEquals(MoveDirection.BACKWARD,directions.get(5));

        simulation.run();
        assertSame(mapAnimals.get(0).getOrientation(), MapDirection.NORTH);
        assertTrue(mapAnimals.get(0).isAt(new Vector2d(2,3)));
        assertSame(mapAnimals.get(1).getOrientation(), MapDirection.WEST);
        assertTrue(mapAnimals.get(1).isAt(new Vector2d(4,4)));
    }

    @Test
    void arePositionAndOrientationCorrectAfterSingleMovement(){
        Animal animal = new Animal(new Vector2d(0,0));
        RectangularMap map = new RectangularMap(5,5);

        animal.move(MoveDirection.BACKWARD,map);
        assertTrue(animal.isAt(new Vector2d(0,0)));
        assertSame(animal.getOrientation(),MapDirection.NORTH);

        animal.move(MoveDirection.FORWARD,map);
        assertTrue(animal.isAt(new Vector2d(0,1)));
        assertSame(animal.getOrientation(),MapDirection.NORTH);

        animal.move(MoveDirection.LEFT,map);
        assertTrue(animal.isAt(new Vector2d(0,1)));
        assertSame(animal.getOrientation(),MapDirection.WEST);

        animal.move(MoveDirection.RIGHT,map);
        assertTrue(animal.isAt(new Vector2d(0,1)));
        assertSame(animal.getOrientation(),MapDirection.NORTH);
    }

    @Test
    void canIsAtMethodBeUsed(){
        Animal animal = new Animal(new Vector2d(1,1));
        assertEquals(animal.getPosition(),new Vector2d(1,1));
        assertTrue(animal.isAt(new Vector2d(1,1)));
    }

    @Test
    void isSimulationListOfAnimalsEqualMapListOfAnimals(){
        List<MoveDirection> directions = new ArrayList<>();
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.LEFT);
        directions.add(MoveDirection.FORWARD);
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal(new Vector2d(3,3)));
        animals.add(new Animal(new Vector2d(3,3)));
        animals.add(new Animal(new Vector2d(3,5)));
        RectangularMap map = new RectangularMap(5,5);
        Simulation<Animal,Vector2d> simulation = new Simulation<Animal, Vector2d>(animals, directions, map);
        List<Animal> mapAnimals = simulation.getMapObjects();
        simulation.run();

        assertEquals(mapAnimals.size(),1);
        Animal animal = mapAnimals.get(0);
        assertSame(animal.getOrientation(), MapDirection.WEST);
        assertTrue(animal.isAt(new Vector2d(2,4)));

        assertEquals(map.getAnimals().size(),1);
        assertTrue(map.isOccupied(new Vector2d(2,4)));
        assertEquals(map.getAnimals().get(new Vector2d(2,4)).getPosition(),new Vector2d(2,4));
        assertEquals(map.getAnimals().get(new Vector2d(2,4)).getOrientation(),MapDirection.WEST);
    }

    @Test
    void areAllValidAnimalsPlacedAndNotValidNotPlaced(){
        List<MoveDirection> directions = new ArrayList<>();
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal(new Vector2d(3,3)));
        animals.add(new Animal(new Vector2d(0,1)));
        animals.add(new Animal(new Vector2d(3,5)));
        animals.add(new Animal(new Vector2d(0,1)));
        animals.add(new Animal(new Vector2d(1,2)));
        RectangularMap map = new RectangularMap(2,3);
        Simulation<Animal, Vector2d> simulation = new Simulation<Animal, Vector2d>(animals, directions, map);
        List<Animal> mapAnimals = simulation.getMapObjects();

        assertEquals(map.getAnimals().size(),2);
        assertEquals(mapAnimals.size(),2);

        assertTrue(map.isOccupied(new Vector2d(0,1)));
        assertTrue(map.isOccupied(new Vector2d(1,2)));
        assertFalse(map.isOccupied(new Vector2d(3,3)));
        assertFalse(map.isOccupied(new Vector2d(3,5)));
    }

    @Test
    void doesMapTracksMovesCorrect(){
        List<MoveDirection> directions = new ArrayList<>();
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.LEFT);
        directions.add(MoveDirection.FORWARD);
//        List<Vector2d> positions = List.of(new Vector2d(3,3), new Vector2d(0,1), new Vector2d(3,5), new Vector2d(0,1), new Vector2d(1,2));
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal(new Vector2d(3,3)));
        animals.add(new Animal(new Vector2d(0,1)));
        animals.add(new Animal(new Vector2d(3,5)));
        animals.add(new Animal(new Vector2d(0,1)));
        animals.add(new Animal(new Vector2d(1,2)));
        RectangularMap map = new RectangularMap(2,3);
        Simulation<Animal, Vector2d> simulation = new Simulation<Animal, Vector2d>(animals, directions, map);

        for (int i=0;i<map.getWidth();i++){
            for (int j=0; j<map.getHeight();j++){
                if (i==0 && j==1 || i==1 && j==2){
                    assertTrue(map.isOccupied(new Vector2d(i,j)));
                }
                else {
                    assertFalse(map.isOccupied(new Vector2d(i,j)));
                }
            }
        }
        assertEquals(map.getAnimals().get(new Vector2d(0,1)).getOrientation(),MapDirection.NORTH);
        assertEquals(map.getAnimals().get(new Vector2d(1,2)).getOrientation(),MapDirection.NORTH);

        simulation.run();
        for (int i=0;i<map.getWidth();i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (i == 0 && j == 2) {
                    assertTrue(map.isOccupied(new Vector2d(i, j)));
                    assertEquals(map.getAnimals().get(new Vector2d(i, j)).getOrientation(), MapDirection.WEST);
                } else if (i == 1 && j == 2) {
                    assertTrue(map.isOccupied(new Vector2d(i, j)));
                    assertEquals(map.getAnimals().get(new Vector2d(i, j)).getOrientation(), MapDirection.NORTH);
                } else {
                    assertFalse(map.isOccupied(new Vector2d(i, j)));
                }
            }
        }
    }

    @Test
    void areAllStringsPlaced(){
        List<MoveDirection> directions = new ArrayList<>();
        List<String> strings = List.of("Ala", "ma", "kota", "i", "psa");
        TextMap map = new TextMap();
        Simulation<String, Integer> simulation = new Simulation<String, Integer>(strings, directions, map);
        List<String> mapStrings = simulation.getMapObjects();

        assertEquals(mapStrings.size(),strings.size());

        for(int i=0;i<strings.size();i++){
            assertEquals(mapStrings.get(i),strings.get(i));
        }
    }

    @Test
    void areStringsMovedCorrect(){
        String[] args = {"f","f","r","l","b","f"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<String> strings = List.of("Ala", "ma", "kota", "i", "psa");
        TextMap map = new TextMap();
        Simulation<String, Integer> simulation = new Simulation<String, Integer>(strings, directions, map);
        List<String> mapStrings = simulation.getMapObjects();
        simulation.run();

        assertEquals(map.objectAt(0),"i");
        assertEquals(map.objectAt(1),"Ala");
        assertEquals(map.objectAt(2),"ma");
        assertEquals(map.objectAt(3),"psa");
        assertEquals(map.objectAt(4),"kota");
    }

    @Test
    void canNotStringsGoOutOfBoundaries(){
        String[] args = {"l","r"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<String> strings = List.of("Ala", "Kowalska");
        TextMap map = new TextMap();
        Simulation<String, Integer> simulation = new Simulation<String, Integer>(strings, directions, map);
        List<String> mapStrings = simulation.getMapObjects();
        simulation.run();

        assertEquals(map.objectAt(0),"Ala");
        assertEquals(map.objectAt(1),"Kowalska");
        assertEquals(map.getStrings().size(),2);
    }

    @Test
    void testSimulationWithWorldCase(){
        String[] args = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<String> mapObjects = List.of("Ala", "ma", "dużego", "psa");
        TextMap map = new TextMap();
        Simulation<String, Integer> simulation = new Simulation<String, Integer>(mapObjects,directions,map);
        simulation.run();

        assertEquals(map.objectAt(0),"ma");
        assertEquals(map.objectAt(1),"psa");
        assertEquals(map.objectAt(2),"Ala");
        assertEquals(map.objectAt(3),"dużego");
        assertEquals(map.getStrings().size(),4);

    }
}