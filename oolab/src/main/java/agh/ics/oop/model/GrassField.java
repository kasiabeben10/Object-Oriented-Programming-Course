package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

import static java.lang.Math.sqrt;

public class GrassField implements WorldMap{
    private final Vector2d lowerLeft = new Vector2d(Integer.MIN_VALUE,Integer.MIN_VALUE);
    private final Vector2d upperRight = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private final MapVisualizer visualizer;
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final Map<Vector2d, Grass> grasses = new HashMap<>();


    public GrassField(int noOfGrass){
        Vector2d grassUpperRight = new Vector2d((int) (sqrt(noOfGrass * 10)), (int) (sqrt(noOfGrass * 10)));
        visualizer = new MapVisualizer(this);

        //naiwne losowanie
        Random random = new Random();
        int i = 0;
        while (i < noOfGrass) {
            int x = random.nextInt(grassUpperRight.getX()+1);
            int y = random.nextInt(grassUpperRight.getY()+1);

            if (grasses.get(new Vector2d(x,y)) == null){
                this.grasses.put(new Vector2d(x,y),new Grass(new Vector2d(x,y)));
                i++;
            }
        }

    }

    public Map<Vector2d, Animal> getAnimals() {
        return animals;
    }
    public Map<Vector2d, Grass> getGrasses() {
        return grasses;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position) instanceof Animal);
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(),animal);
            return true;
        }
        return false;
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        animals.remove(animal.getPosition());
        animal.move(direction, this);
        animals.put(animal.getPosition(),animal);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position)!=null ? animals.get(position) : grasses.get(position);
    }

    @Override
    public String toString(){
        Vector2d stringUpperRight = new Vector2d(lowerLeft.getX(), lowerLeft.getY());
        Vector2d stringLowerLeft = new Vector2d(upperRight.getX(), upperRight.getY());
        for (WorldElement animal : animals.values()) {
            stringUpperRight = stringUpperRight.upperRight(animal.getPosition());
            stringLowerLeft = stringLowerLeft.lowerLeft(animal.getPosition());
        }
        for (WorldElement grass : grasses.values()) {
            stringUpperRight = stringUpperRight.upperRight(grass.getPosition());
            stringLowerLeft = stringLowerLeft.lowerLeft(grass.getPosition());
        }
        return visualizer.draw(stringLowerLeft,stringUpperRight);
    }
}
