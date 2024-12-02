package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.util.IncorrectPositionException;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable{
    private List<Animal> animals = new ArrayList<>();
    private List<MoveDirection> directions;
    private final WorldMap map;

    public Simulation(List<Vector2d> positions, List<MoveDirection> directions, WorldMap map){
        this.directions = directions;
        this.map = map;
        for (Vector2d position : positions) {
            Animal animal = new Animal(position);
            try {
                map.place(animal);
                animals.add(animal);
            } catch (IncorrectPositionException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<MoveDirection> getDirections() {
        return directions;
    }

    public void run(){
        if (animals.isEmpty()){
            return;
        }
        for (int i=0; i<directions.size();i++){
            int animal_no = i % animals.size();
            map.move(animals.get(animal_no),directions.get(i));
        }
    }
}
