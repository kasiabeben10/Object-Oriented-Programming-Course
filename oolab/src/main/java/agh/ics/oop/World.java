package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;


public class World {
    public static void main(String[] args){
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<String> mapObjects = List.of("Ala", "ma", "dużego", "psa");
        Simulation<String, Integer> simulation = new Simulation<String, Integer>(mapObjects,directions,new TextMap());
        simulation.run();
    }
    public static void run(MoveDirection[] directions){
        for (MoveDirection direction: directions){
            switch (direction){
                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("Zwierzak skręca w prawo");
                case LEFT -> System.out.println("Zwierzak skręca w lewo");
            };
        }
    }
}
