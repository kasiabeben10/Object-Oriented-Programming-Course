package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;
import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap{
    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField(int noOfGrass){
        int maxWidth = (int)sqrt(10*noOfGrass);
        int maxHeight = (int)sqrt(10*noOfGrass);

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
    public List<WorldElement> getElements() {
        List<WorldElement> elements = super.getElements();
        elements.addAll(grasses.values());
        return elements;
    }

    @Override
    public Boundary getCurrentBounds() {
        List<WorldElement> mapElements = this.getElements();
        if (mapElements.isEmpty()) {
            return new Boundary(new Vector2d(0,0), new Vector2d(0,0));
        }
        Vector2d currentUpperRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        Vector2d currentLowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (WorldElement mapElement : mapElements){
            currentUpperRight = currentUpperRight.upperRight(mapElement.getPosition());
            currentLowerLeft = currentLowerLeft.lowerLeft(mapElement.getPosition());
        }

        return new Boundary(currentLowerLeft,currentUpperRight);
    }
}
