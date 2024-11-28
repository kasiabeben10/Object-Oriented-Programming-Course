package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;


public class World {
    public static void main(String[] args) {
        try {
            MapChangeListener observer = new ConsoleMapDisplay();
            List<AbstractWorldMap> maps = new ArrayList<>();
            for (int i=0; i<2000; i++){
                GrassField map = new GrassField(10);
                RectangularMap map2 = new RectangularMap(10,10);
                map.addObserver(observer);
                map2.addObserver(observer);
                maps.add(map);
                maps.add(map2);
            }
            List<Simulation> simulations = new ArrayList<>();
            for (AbstractWorldMap map: maps){
                List<MoveDirection> directions = OptionsParser.parse(args);
                List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4), new Vector2d(2, 2));
                Simulation simulation = new Simulation(positions, directions, map);
                simulations.add(simulation);
            }
            SimulationEngine simulationEngine = new SimulationEngine(simulations);
//            simulationEngine.runAsync();
            simulationEngine.runAsyncInThreadPool();
        } catch (IllegalArgumentException e){
            System.out.println("EXCEPTION: \n" + e.getMessage());
            System.out.println("Warning: Program failed!!!");
            return;
        }
        System.out.println("system zakończył działanie");
    }

}
