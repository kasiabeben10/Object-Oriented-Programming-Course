package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d>{
    private final int maxWidth;
    private final int maxHeight;
    private final int noOfGrasses;
    private final Vector2d[] allPositions;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int noOfGrasses) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.noOfGrasses = noOfGrasses;
        this.allPositions = getAllPositions();
    }

    private Vector2d[] getAllPositions() {
        Vector2d[] positions = new Vector2d[maxHeight*maxWidth];
        int cnt = 0;
        for (int x=0; x<maxWidth; x++) {
            for (int y=0; y<maxHeight; y++) {
                positions[cnt++] = new Vector2d(x, y);
            }
        }
        return positions;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<>() {
            private int generated = 0;
            private final Random random = new Random();

            @Override
            public boolean hasNext() {
                return generated < noOfGrasses;
            }

            @Override
            public Vector2d next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("All generated");
                }
                // z tablicy losujemy pozycję z indeksów od 0 do length-generated,
                // która jeszcze nie była wylosowana
                // po wylosowaniu i zapamiętaniu wylosowanego Vextora2d,
                // na pozycję elementu wylosowanego
                // wstawiamy ostanią możliwą pozycję z zakresu jeszcze nie wylosowanych
                // pozycji i zawężamy zakres
                // z którego będziemy losować następnym razem -
                // zapewniamy to zwiększając licznik generated

                int randomPos = random.nextInt(allPositions.length-generated);
                Vector2d generatedPos = allPositions[randomPos];
                allPositions[randomPos] = allPositions[allPositions.length-generated-1];
                generated++;
                return generatedPos;
            }
        };
    }
}
