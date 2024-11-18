package agh.ics.oop.model;


public class RectangularMap extends AbstractWorldMap {

    public RectangularMap(int width, int height){
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width-1, height-1);
    }

    @Override
    public String toString() {
        return visualizer.draw(lowerLeft,upperRight);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && position.follows(lowerLeft) && position.precedes(upperRight);
    }
}
