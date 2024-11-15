package agh.ics.oop.model;

import java.util.*;
import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap{
    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField(int noOfGrass){
        Vector2d grassUpperRight = new Vector2d((int) (sqrt(noOfGrass * 10)), (int) (sqrt(noOfGrass * 10)));

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
