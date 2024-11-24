package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;


public class World {
    public static void main(String[] args) {

        try {
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4), new Vector2d(2, 2));
            GrassField map = new GrassField((10));
            //RectangularMap map = new RectangularMap(10,10);
            map.addObserver(new ConsoleMapDisplay());
            Simulation simulation = new Simulation(positions, directions, map);
            simulation.run();
        } catch (IllegalArgumentException e){
            System.out.println("EXCEPTION: \n" + e.getMessage());
            System.out.println("Warning: Program failed!!!");
            return; //przerwanie programu
        }
    }

}
