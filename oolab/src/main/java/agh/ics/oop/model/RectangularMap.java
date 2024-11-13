package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class RectangularMap implements WorldMap {
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final int width;
    private final int height;
    private final Vector2d lowerLeft = new Vector2d(0,0);
    private final Vector2d upperRight;
    private final MapVisualizer mapVisualiser;

    public RectangularMap(int width, int height){
        this.width = width;
        this.height = height;
        this.mapVisualiser = new MapVisualizer(this);
        this.upperRight = new Vector2d(this.width-1, this.height-1);
    }

    public Map<Vector2d, Animal> getAnimals() {
        return animals;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    public Vector2d getUpperRight() {
        return upperRight;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position) && position.follows(lowerLeft) && position.precedes(upperRight);
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
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
        return (objectAt(position) != null);
    }

    @Override
    public Animal objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public String toString() {
        return mapVisualiser.draw(lowerLeft,upperRight);
    }
}
