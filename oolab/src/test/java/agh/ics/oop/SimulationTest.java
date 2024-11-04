package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static agh.ics.oop.model.Animal.LOWER_LEFT;
import static agh.ics.oop.model.Animal.UPPER_RIGHT;
import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void areResultsCorrectAndTheSameForEqualAnimalsAndTheSameOrders(){
        List<MoveDirection> directions = new ArrayList<>();
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.LEFT);
        directions.add(MoveDirection.LEFT);
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.FORWARD);
        List<Vector2d> positions = List.of(new Vector2d(3,3), new Vector2d(3,3));
        Simulation simulation = new Simulation(positions, directions);
        List<Animal> animals = simulation.getAnimals();
        simulation.run();

        assertEquals(positions.size(), animals.size());

        for (Animal animal : animals) {
            assertSame(animal.getOrientation(), MapDirection.WEST);
            assertTrue(animal.isAt(new Vector2d(2,4)));
            assertTrue(animal.getPosition().follows(LOWER_LEFT));
            assertTrue(animal.getPosition().precedes(UPPER_RIGHT));
        }
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
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(4,3), new Vector2d(0,1));
        Simulation simulation = new Simulation(positions, directions);
        List<Animal> animals = simulation.getAnimals();

        for (int i=0;i<positions.size();i++){
            assertSame(animals.get(i).getOrientation(), MapDirection.NORTH);
            assertTrue(animals.get(i).isAt(positions.get(i)));
        }

        simulation.run();
        assertSame(animals.get(0).getOrientation(), MapDirection.EAST);
        assertTrue(animals.get(0).isAt(new Vector2d(2,3)));
        assertSame(animals.get(1).getOrientation(), MapDirection.WEST);
        assertTrue(animals.get(1).isAt(new Vector2d(4,3)));
        assertSame(animals.get(2).getOrientation(), MapDirection.NORTH);
        assertTrue(animals.get(2).isAt(new Vector2d(0,0)));
    }


    @Test
    void canNotAnimalBeOutOfBoundaries(){
        Animal animal = new Animal(new Vector2d(0,0));

        //bottom
        animal.move(MoveDirection.BACKWARD);
        assertEquals(animal.getPosition(),new Vector2d(0,0));
        assertTrue(animal.getPosition().follows(LOWER_LEFT));
        assertTrue(animal.getPosition().precedes(UPPER_RIGHT));


        //left
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getPosition(),new Vector2d(0,0));
        assertTrue(animal.getPosition().follows(LOWER_LEFT));
        assertTrue(animal.getPosition().precedes(UPPER_RIGHT));

        //top
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getPosition(),new Vector2d(0,4));
        assertTrue(animal.getPosition().follows(LOWER_LEFT));
        assertTrue(animal.getPosition().precedes(UPPER_RIGHT));

        //right
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getPosition(),new Vector2d(4,4));
        assertTrue(animal.getPosition().follows(LOWER_LEFT));
        assertTrue(animal.getPosition().precedes(UPPER_RIGHT));
    }

    @Test
    void areStringArgsInterpretedCorrect(){
        String[] args = {"f","l","x","r","b","e","l","b"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        Simulation simulation = new Simulation(positions, directions);
        List<Animal> animals = simulation.getAnimals();

        for (int i=0;i<positions.size();i++){
            assertSame(animals.get(i).getOrientation(), MapDirection.NORTH);
            assertEquals(animals.get(i).getPosition(),positions.get(i));
        }

        simulation.run();
        assertSame(animals.get(0).getOrientation(), MapDirection.NORTH);
        assertTrue(animals.get(0).isAt(new Vector2d(2,3)));
        assertSame(animals.get(1).getOrientation(), MapDirection.WEST);
        assertTrue(animals.get(1).isAt(new Vector2d(4,4)));
    }

    @Test
    void arePositionAndOrientationCorrectAfterSingleMovement(){
        Animal animal = new Animal(new Vector2d(0,0));

        animal.move(MoveDirection.BACKWARD);
        assertTrue(animal.isAt(new Vector2d(0,0)));
        assertSame(animal.getOrientation(),MapDirection.NORTH);

        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(0,1)));
        assertSame(animal.getOrientation(),MapDirection.NORTH);

        animal.move(MoveDirection.LEFT);
        assertTrue(animal.isAt(new Vector2d(0,1)));
        assertSame(animal.getOrientation(),MapDirection.WEST);

        animal.move(MoveDirection.RIGHT);
        assertTrue(animal.isAt(new Vector2d(0,1)));
        assertSame(animal.getOrientation(),MapDirection.NORTH);
    }

    @Test
    void canIsAtMethodBeUsed(){
        Animal animal = new Animal(new Vector2d(1,1));
        assertEquals(animal.getPosition(),new Vector2d(1,1));
        assertTrue(animal.isAt(new Vector2d(1,1)));
    }
}