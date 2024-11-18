package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;
import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap{
    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField(int noOfGrass){
        int maxWidth = (int)sqrt(10*noOfGrass);
        int maxHeight = (int)sqrt(10*noOfGrass);
        Vector2d grassUpperRight = new Vector2d(maxWidth, maxHeight);

        //deterministyczne losowanie pozycji kępek trawy
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(maxWidth, maxHeight, noOfGrass);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
    }

    public Map<Vector2d, Grass> getGrasses() {
        return grasses;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement objectAtPosition = super.objectAt(position);
        if(objectAtPosition != null) return objectAtPosition;
        return grasses.get(position);
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

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> elements = super.getElements();
        elements.addAll(grasses.values());
        return elements;
    }
}
