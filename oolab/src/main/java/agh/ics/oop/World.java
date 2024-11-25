package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;


public class World {
    public static void main(String[] args) {

        try {
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4), new Vector2d(2, 2));
            GrassField map1 = new GrassField((10));
            RectangularMap map2 = new RectangularMap(10,10);
            map1.addObserver(new ConsoleMapDisplay());
            map2.addObserver(new ConsoleMapDisplay());
            Simulation simulation1 = new Simulation(positions, directions, map1);
            Simulation simulation2 = new Simulation(positions, directions, map2);
            SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation1, simulation2));
//            simulationEngine.runSync();
            simulationEngine.runAsync();
        } catch (IllegalArgumentException e){
            System.out.println("EXCEPTION: \n" + e.getMessage());
            System.out.println("Warning: Program failed!!!");
            return;
        }
        System.out.println("system zakończył działanie");
    }

}
