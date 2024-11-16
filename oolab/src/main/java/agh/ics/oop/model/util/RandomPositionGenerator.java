package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d>{
    private final int maxWidth;
    private final int maxHeight;
    private final int noOfGrasses;
    private final List<Vector2d> allPositions;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int noOfGrasses) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.noOfGrasses = noOfGrasses;
        this.allPositions = getAllPositions();
    }

    private List<Vector2d> getAllPositions() {
        List<Vector2d> positions = new ArrayList<>();
        for (int x=0; x<maxWidth; x++) {
            for (int y=0; y<maxHeight; y++) {
                positions.add(new Vector2d(x, y));
            }
        }
        return positions;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<>() {
            private int generated = 0;

            @Override
            public boolean hasNext() {
                return generated < noOfGrasses;
            }

            @Override
            public Vector2d next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("All generated");
                }
                Collections.shuffle(allPositions);
                Vector2d generatedPos = allPositions.getLast();
                allPositions.removeLast();
                generated++;
                return generatedPos;
            }
        };
    }
}
