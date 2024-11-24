package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;


public class World {
    public static void main(String[] args) {
        try {
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4), new Vector2d(2, 2));
            Simulation simulation = new Simulation(positions, directions, new GrassField(10));
            simulation.run();
        } catch (IllegalArgumentException e){
            System.out.println("EXCEPTION: \n" + e.getMessage());
            System.out.println("Warning: Program failed!!!");
            return; //przerwanie programu
        }
    }

}
