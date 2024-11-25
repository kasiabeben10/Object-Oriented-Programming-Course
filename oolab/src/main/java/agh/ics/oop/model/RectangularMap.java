package agh.ics.oop.model;


import agh.ics.oop.model.util.Boundary;

public class RectangularMap extends AbstractWorldMap {
    private final int width;
    private final int height;

    public RectangularMap(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        Boundary b = this.getCurrentBounds();
        return super.canMoveTo(position) && position.follows(b.lowerLeft()) && position.precedes(b.upperRight());
    }

    @Override
    public Boundary getCurrentBounds(){
        return new Boundary(new Vector2d(0,0), new Vector2d(this.width-1, this.height-1));
    }
}
