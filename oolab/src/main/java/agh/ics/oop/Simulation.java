package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private List<Animal> animals = new ArrayList<>();
    private List<MoveDirection> directions;
    //w naszym przypadku lepiej użyć arraylist niz linked list, aby móc
    //korzystać z łatwego dostępu do wybranego elementu
    //ma to znaczenie np. dla metody run w której odwołujemy się do elementu listy
    //po indeksie aby odpowiednio wykonywać wskazane ruchy

    public Simulation(List<Vector2d> positions, List<MoveDirection> directions){
        for (Vector2d position : positions){
            animals.add(new Animal(position));
        }
        this.directions = directions;
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
            animals.get(animal_no).move(directions.get(i));
            System.out.println("Zwierzę " + animal_no + " : " + animals.get(animal_no));
        }
    }
}
