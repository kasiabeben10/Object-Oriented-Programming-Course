package agh.ics.oop.model;

import static agh.ics.oop.model.MapDirection.NORTH;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;

    public Animal(){
        this(new Vector2d(2,2));
    }

    public Animal(Vector2d position){
        this.position = position;
    }


    @Override
    public String toString() {
        return this.orientation.toString();
    }

    public boolean isAt(Vector2d position){
        return (this.position.equals(position));
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void move(MoveDirection direction, MoveValidator validator){

        switch(direction){
            case RIGHT -> this.orientation = this.orientation.next();
            case LEFT -> this.orientation = this.orientation.previous();
            case FORWARD -> {
                Vector2d nextPos = this.position.add(this.orientation.toUnitVector());
                if(validator.canMoveTo(nextPos)) {
                    this.position = nextPos;
                }
            }
            case BACKWARD -> {
                Vector2d nextPos = this.position.subtract(this.orientation.toUnitVector());
                if(validator.canMoveTo(nextPos)) {
                    this.position = nextPos;
                }
            }


        }
    }
}
