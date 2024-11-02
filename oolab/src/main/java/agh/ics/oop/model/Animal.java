package agh.ics.oop.model;

import static agh.ics.oop.model.MapDirection.NORTH;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    public static final Vector2d LOWER_LEFT = new Vector2d(0,0);
    public static final Vector2d UPPER_RIGHT = new Vector2d(4,4);

    public Animal(){
        this(new Vector2d(2,2));
    }

    public Animal(Vector2d position){
        this.position = position;
    }


    @Override
    public String toString() {
        return "{" + position +
                ", " + orientation +
                "}";
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

    public void move(MoveDirection direction){

        switch(direction){
            case RIGHT -> this.orientation = this.orientation.next();
            case LEFT -> this.orientation = this.orientation.previous();
            case FORWARD -> {
                Vector2d nextPos = this.position.add(this.orientation.toUnitVector());
                if(nextPos.precedes(UPPER_RIGHT) && nextPos.follows(LOWER_LEFT)) {
                    this.position = nextPos;
                }
            }
            case BACKWARD -> {
                Vector2d nextPos = this.position.subtract(this.orientation.toUnitVector());
                if(nextPos.precedes(UPPER_RIGHT) && nextPos.follows(LOWER_LEFT)) {
                    this.position = nextPos;
                }
            }


        }
    }
}
