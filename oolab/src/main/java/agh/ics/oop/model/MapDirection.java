package agh.ics.oop.model;

public enum MapDirection {
    NORTH, SOUTH, WEST, EAST;

    public String toString(){
        return switch (this) {
            case EAST -> "E";
            case WEST -> "W";
            case SOUTH -> "S";
            case NORTH -> "N";
        };
    }
    public MapDirection next(){
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    public MapDirection previous(){
        return switch (this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
    }

    public Vector2d toUnitVector(){
        return switch (this) {
            case EAST -> new Vector2d(1,0);
            case WEST -> new Vector2d(-1,0);
            case SOUTH -> new Vector2d(0,-1);
            case NORTH -> new Vector2d(0,1);
        };
    }

}
