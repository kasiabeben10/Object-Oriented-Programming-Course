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
        List<Vector2d> positions = List.of(new Vector2d(3,3), new Vector2d(3,3), new Vector2d(3,5));
        RectangularMap map = new RectangularMap(5,5);
        Simulation simulation = new Simulation(positions, directions, map);
        List<Animal> animals = simulation.getAnimals();
        simulation.run();

        assertEquals(animals.size(),1);
        Animal animal = animals.get(0);
        assertSame(animal.getOrientation(), MapDirection.WEST);
        assertTrue(animal.isAt(new Vector2d(2,4)));
        assertTrue(animal.getPosition().follows(map.getCurrentBounds().lowerLeft()));
        assertTrue(animal.getPosition().precedes(map.getCurrentBounds().upperRight()));
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
        RectangularMap map = new RectangularMap(8,8);
        Simulation simulation = new Simulation(positions, directions, map);
        List<Animal> animals = simulation.getAnimals();

        for (int i=0;i<positions.size();i++){
            assertSame(animals.get(i).getOrientation(), MapDirection.NORTH);
            assertTrue(animals.get(i).isAt(positions.get(i)));
        }

        simulation.run();
        assertSame(animals.get(0).getOrientation(), MapDirection.EAST);
        assertTrue(animals.get(0).isAt(new Vector2d(2,3)));
        assertSame(animals.get(1).getOrientation(), MapDirection.WEST);
        assertTrue(animals.get(1).isAt(new Vector2d(5,3)));
        assertSame(animals.get(2).getOrientation(), MapDirection.NORTH);
        assertTrue(animals.get(2).isAt(new Vector2d(0,0)));
    }


    @Test
    void canNotAnimalBeOutOfBoundaries(){
        Animal animal = new Animal(new Vector2d(0,0));
        RectangularMap map = new RectangularMap(5,5);

        //bottom
        animal.move(MoveDirection.BACKWARD, map);
        assertEquals(animal.getPosition(),new Vector2d(0,0));
        assertTrue(animal.getPosition().follows(map.getCurrentBounds().lowerLeft()));
        assertTrue(animal.getPosition().precedes(map.getCurrentBounds().upperRight()));


        //left
        animal.move(MoveDirection.LEFT, map);
        animal.move(MoveDirection.FORWARD, map);
        assertEquals(animal.getPosition(),new Vector2d(0,0));
        assertTrue(animal.getPosition().follows(map.getCurrentBounds().lowerLeft()));
        assertTrue(animal.getPosition().precedes(map.getCurrentBounds().upperRight()));

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
        assertTrue(animal.getPosition().follows(map.getCurrentBounds().lowerLeft()));
        assertTrue(animal.getPosition().precedes(map.getCurrentBounds().upperRight()));

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
        assertTrue(animal.getPosition().follows(map.getCurrentBounds().lowerLeft()));
        assertTrue(animal.getPosition().precedes(map.getCurrentBounds().upperRight()));
    }

    @Test
    void areStringArgsInterpretedCorrect(){
        String[] args = {"f","l","r","b","l","b"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        Simulation simulation = new Simulation(positions, directions,new RectangularMap(5,5));
        List<Animal> animals = simulation.getAnimals();

        for (int i=0;i<positions.size();i++){
            assertSame(animals.get(i).getOrientation(), MapDirection.NORTH);
            assertEquals(animals.get(i).getPosition(),positions.get(i));
        }
        assertEquals(MoveDirection.FORWARD,directions.get(0));
        assertEquals(MoveDirection.LEFT,directions.get(1));
        assertEquals(MoveDirection.RIGHT,directions.get(2));
        assertEquals(MoveDirection.BACKWARD,directions.get(3));
        assertEquals(MoveDirection.LEFT,directions.get(4));
        assertEquals(MoveDirection.BACKWARD,directions.get(5));

        simulation.run();
        assertSame(animals.get(0).getOrientation(), MapDirection.NORTH);
        assertTrue(animals.get(0).isAt(new Vector2d(2,3)));
        assertSame(animals.get(1).getOrientation(), MapDirection.WEST);
        assertTrue(animals.get(1).isAt(new Vector2d(4,4)));
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
        List<Vector2d> positions = List.of(new Vector2d(3,3), new Vector2d(3,3), new Vector2d(3,5));
        RectangularMap map = new RectangularMap(5,5);
        Simulation simulation = new Simulation(positions, directions, map);
        List<Animal> animals = simulation.getAnimals();
        simulation.run();

        assertEquals(animals.size(),1);
        Animal animal = animals.get(0);
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
        List<Vector2d> positions = List.of(new Vector2d(3,3), new Vector2d(0,1), new Vector2d(3,5), new Vector2d(0,1), new Vector2d(1,2));
        RectangularMap map = new RectangularMap(2,3);
        Simulation simulation = new Simulation(positions, directions, map);
        List<Animal> animals = simulation.getAnimals();

        assertEquals(map.getAnimals().size(),2);
        assertEquals(animals.size(),2);

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
        List<Vector2d> positions = List.of(new Vector2d(3,3), new Vector2d(0,1), new Vector2d(3,5), new Vector2d(0,1), new Vector2d(1,2));
        RectangularMap map = new RectangularMap(2,3);
        Simulation simulation = new Simulation(positions, directions, map);

        int width = map.getCurrentBounds().upperRight().getX()-map.getCurrentBounds().lowerLeft().getX()+1;
        int height = map.getCurrentBounds().upperRight().getY()-map.getCurrentBounds().lowerLeft().getY()+1;
        for (int i=0;i<width;i++){
            for (int j=0; j<height;j++){
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
        for (int i=0;i<width;i++){
            for (int j=0; j<height;j++){
                if (i==0 && j==2){
                    assertTrue(map.isOccupied(new Vector2d(i,j)));
                    assertEquals(map.getAnimals().get(new Vector2d(i,j)).getOrientation(),MapDirection.WEST);
                }
                else if (i==1 && j==2){
                    assertTrue(map.isOccupied(new Vector2d(i,j)));
                    assertEquals(map.getAnimals().get(new Vector2d(i,j)).getOrientation(),MapDirection.NORTH);
                }
                else {
                    assertFalse(map.isOccupied(new Vector2d(i,j)));
                }
            }
        }



    }
}