package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap{
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    private final List<MapChangeListener> listeners = new ArrayList<>();

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position) instanceof Animal);
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException{
        if (canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(),animal);
            this.mapChanged("New animal placed at %s".formatted(animal.getPosition()));
        }
        else throw new IncorrectPositionException(animal.getPosition());
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        MapDirection oldDirection = animal.getOrientation();
        animals.remove(animal.getPosition());
        animal.move(direction, this);
        animals.put(animal.getPosition(),animal);
        if (!animal.isAt(oldPosition) || oldDirection!=animal.getOrientation()){
            mapChanged("Animal moved from %s%s to %s%s".formatted(oldPosition, oldDirection, animal.getPosition(), animal.getOrientation()));
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    public Map<Vector2d, Animal> getAnimals() {
        return animals;
    }

    @Override
    public List<WorldElement> getElements() {
        return new ArrayList<>(animals.values());
    }

    @Override
    public final String toString() {
        Boundary boundary = this.getCurrentBounds();
        return visualizer.draw(boundary.lowerLeft(), boundary.upperRight());
    }

    private void mapChanged(String message) {
        for (MapChangeListener listener : listeners){
            listener.mapChanged(this,message);
        }
    }
    public void addObserver(MapChangeListener listener){
        listeners.add(listener);
    }
    public void removeObserver(MapChangeListener listener){
        listeners.remove(listener);
    }

}
